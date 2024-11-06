package com.dev.ritividhi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dev.ritividhi.models.OfflinePuja;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.OfflinePujaService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;

@RestController
@RequestMapping("/ritividhi/offline-puja")
public class OfflinePujaController {

	@Autowired
	OfflinePujaService offlinePujaService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@GetMapping
	public ResponseEntity<List<OfflinePuja>> getAllOfflinePujas() {
		List<OfflinePuja> pujas = offlinePujaService.getAllOfflinePujas();
		return ResponseEntity.ok(pujas);
	}

	@GetMapping("/{pujaId}")
	public ResponseEntity<OfflinePuja> getPujaById(@PathVariable String pujaId) {
		Optional<OfflinePuja> puja = offlinePujaService.findById(pujaId);
		return puja.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> createPuja(@RequestBody OfflinePuja offlinePuja,
			@RequestHeader(name = "Authorization") String bearerToken) {
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
				OfflinePuja savedPuja = offlinePujaService.save(offlinePuja);
				return ResponseEntity.ok(savedPuja);
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Forbidden: You do not have permission to access this resource"); // Forbidden with
																								// message
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
					offlinePujaService.deletePuja(pujaId);
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
//    @Autowired
//    OfflinePujaService offlinePujaService;
//
//
//    @GetMapping
//    public ResponseEntity<List<OfflinePuja>> getAllOfflinePujas() {
//        List<OfflinePuja> pujas = offlinePujaService.getAllOfflinePujas();
//        return ResponseEntity.ok(pujas);
//    }
//    
//
//    @GetMapping("/{pujaId}")
//    public ResponseEntity<OfflinePuja> getPujaById(@PathVariable String pujaId) {
//        Optional<OfflinePuja> puja = offlinePujaService.findById(pujaId);
//        return puja.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping
//    public ResponseEntity<OfflinePuja> createPuja(@RequestBody OfflinePuja offlinePuja) {
//        OfflinePuja savedPuja = offlinePujaService.save(offlinePuja);
//        return ResponseEntity.ok(savedPuja);
//    }
//
//
//	@DeleteMapping("/{pujaId}")
//	public ResponseEntity<Void> deletePuja(@PathVariable String pujaId) {
//		try {
//			offlinePujaService.deletePuja(pujaId);
//			return ResponseEntity.ok().build();
//		} catch (RuntimeException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
//}
