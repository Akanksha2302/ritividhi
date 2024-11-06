package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.OnlineEvents;
import com.dev.ritividhi.services.OnlineEventsService;
import com.dev.ritividhi.utils.CommonUtils;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.services.AdminEmailAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/online-events")
public class OnlineEventsController {

	@Autowired
	private OnlineEventsService onlineEventsService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody OnlineEvents onlineEvent,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		if (bearerToken == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
	    }
		
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
		}

		Optional<User> currentUser = userService.getCurrentUser(token);

		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();

			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				OnlineEvents savedEvent = onlineEventsService.save(onlineEvent);
				return ResponseEntity.ok(savedEvent);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Forbidden: You do not have permission to access this resource");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized");
		}
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<OnlineEvents> getEventById(@PathVariable String eventId) {
		Optional<OnlineEvents> event = onlineEventsService.findById(eventId);
		return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<OnlineEvents>> getAllEvents() {
		List<OnlineEvents> events = onlineEventsService.findAll();
		return ResponseEntity.ok(events);
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<?> updateEvent(@PathVariable String eventId, @RequestBody OnlineEvents onlineEvent,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);

		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
		}

		Optional<User> currentUser = userService.getCurrentUser(token);

		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();

			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				Optional<OnlineEvents> existingEvent = onlineEventsService.findById(eventId);
				if (existingEvent.isPresent()) {
					onlineEvent.setTempleId(eventId); // Ensure the ID is maintained
					onlineEventsService.save(onlineEvent);
					return ResponseEntity.ok(onlineEvent);
				} else {
					return ResponseEntity.notFound().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Forbidden: You do not have permission to update this event");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized");
		}
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<String> deleteEvent(@PathVariable String eventId,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);

		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
		}

		Optional<User> currentUser = userService.getCurrentUser(token);

		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();

			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				try {
					onlineEventsService.delete(eventId);
					return ResponseEntity.ok().build();
				} catch (RuntimeException e) {
					return ResponseEntity.notFound().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Forbidden: You do not have permission to delete this event");
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized");
		}
	}
}
