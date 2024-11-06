package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.Event;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.EventService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/events")
@Validated // Enable validation for the request body
public class EventController {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserService userService;

	@Autowired
	AdminEmailAuthService adminEmailAuthService;

	// Create a new event
	@PostMapping
	public ResponseEntity<Map<String, Object>> createEvent(@RequestBody @Validated Event event, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		// Check for validation errors
		if (result.hasErrors()) {
			response.put("status", "error");
			response.put("message", result.getFieldError().getDefaultMessage()); // Get simplified message
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
		}

		try {
			// Save the event
			Event createdEvent = eventService.createEvent(event);
			response.put("status", "success");
			response.put("message", "Event created successfully.");
			response.put("data", createdEvent);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "An error occurred while creating the event: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	// DELETE method to delete an Event by eventId
	@DeleteMapping("/{eventId}")
	public ResponseEntity<String> deleteEvent(@PathVariable("eventId") String eventId,
			@RequestHeader(name = "Authorization") String bearerToken) {
		// Extract the token
		String token = CommonUtils.extractBearerToken(bearerToken);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
		}

		// Get the current user using the token
		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();

			// Check if the user is authorized
			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				// Attempt to delete the event
				boolean deleted = eventService.deleteEventById(eventId);
				return deleted ? ResponseEntity.noContent().build() // 204 No Content
						: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found"); // 404 if not found
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to delete events");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
		}
	}

}
