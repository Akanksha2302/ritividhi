package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.OfflinePuja;
import com.dev.ritividhi.models.OnlinePuja;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.repository.OnlinePujaRepository;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.OnlinePujaService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;
import com.dev.ritividhi.models.OnlinePuja;
import com.dev.ritividhi.repository.OnlinePujaRepository;
import com.dev.ritividhi.services.OnlinePujaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/online-puja")
public class OnlinePujaController {

	@Autowired
	private OnlinePujaService onlinePujaService;

	@Autowired
	private OnlinePujaRepository onlinePujaRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	// GET method to retrieve all Online Pujas
	@GetMapping
	public ResponseEntity<List<OnlinePuja>> getAllOnlinePujas() {
		List<OnlinePuja> onlinePujas = onlinePujaRepository.findAll();
		return ResponseEntity.ok(onlinePujas);
	}

	// GET method to retrieve a specific Online Puja by ID
	@GetMapping("/{id}")
	public ResponseEntity<OnlinePuja> getOnlinePujaById(@PathVariable String id) {
		return onlinePujaRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// POST method to create a new Online Puja
	@PostMapping

	public ResponseEntity<?> createOnlinePuja(@RequestBody OnlinePuja onlinePuja,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		if (token == null) {
			return ResponseEntity.status(401).body("Missing or invalid token"); // Unauthorized with message
		}

		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent() && adminEmailAuthService.isEmailAuthorized(currentUser.get().getEmail())) {
			OnlinePuja savedOnlinePuja = onlinePujaService.saveOnlinePuja(onlinePuja);
			return ResponseEntity.status(201).body(savedOnlinePuja);
		} else {
			return ResponseEntity.status(403).body("Forbidden: You do not have permission to access this resource");
		} // Forbidden with message
	}

	public ResponseEntity<OnlinePuja> createOnlinePuja(@RequestBody OnlinePuja onlinePuja) {
		OnlinePuja savedOnlinePuja = onlinePujaService.saveOnlinePuja(onlinePuja);
		return ResponseEntity.status(201).body(savedOnlinePuja);

	}

	@DeleteMapping("/{pujaId}")
	public ResponseEntity<String> deletePuja(@PathVariable String pujaId,
			@RequestHeader(name = "Authorization") String bearerToken) {

		// Extract the token
		String token = CommonUtils.extractBearerToken(bearerToken);

		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token"); // Unauthorized with
																									// message
		}

		// Get the current user using the token
		Optional<User> currentUser = userService.getCurrentUser(token);

		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();

			// Check if the user is authorized
			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				try {
					// Call the service to delete the puja
					onlinePujaService.deletePuja(pujaId);
					return ResponseEntity.ok().build(); // Responds with 200 OK
				} catch (RuntimeException e) {
					return ResponseEntity.notFound().build(); // Responds with 404 Not Found if puja doesn't exist
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Forbidden: You do not have permission to delete this puja"); // Forbidden message
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized"); // Forbidden
																													// if
																													// user
																													// is
																													// not
																													// found
		}
	}

}
