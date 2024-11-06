package com.dev.ritividhi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.ritividhi.models.Puja;
import com.dev.ritividhi.models.PujaSummary;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.PujaService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;

@RestController
@RequestMapping("/ritividhi/puja")
public class PujaController {

	@Autowired
	PujaService pujaService;

	@Autowired
	UserService userService;

	@Autowired
	AdminEmailAuthService adminEmailAuthService;

	@GetMapping
	public List<Puja> getAllPujas() {
		return pujaService.findAll();
	}

	@GetMapping("/{pujaId}")
	public ResponseEntity<Puja> getPujaById(@PathVariable String pujaId) {
		Optional<Puja> puja = pujaService.findById(pujaId);
		if (puja.isPresent()) {
			return ResponseEntity.ok(puja.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<?> createPuja(@RequestBody Puja puja,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		if (token == null) {
			return ResponseEntity.status(401).body("Missing or invalid token"); // Unauthorized with message
		}

		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();

			// Check if the user is authorized
			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				Puja createdPuja = pujaService.savePuja(puja);
				return new ResponseEntity<>(createdPuja, HttpStatus.CREATED);
			} else {
				return ResponseEntity.status(403).body("Forbidden: You do not have permission to access this resource"); // Forbidden
																															// with
																															// message
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized"); // Forbidden
																													// if
																													// user
																													// not
																													// found
		}
	}
	
	@DeleteMapping("/{pujaId}")
	public ResponseEntity<String> deletePuja(
	        @PathVariable String pujaId,
	        @RequestHeader(name = "Authorization") String bearerToken) {

	    // Extract the token
	    String token = CommonUtils.extractBearerToken(bearerToken);
	    
	    if (token == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                             .body("Missing or invalid token"); // Unauthorized with message
	    }

	    // Get the current user using the token
	    Optional<User> currentUser = userService.getCurrentUser(token);
	    
	    if (currentUser.isPresent()) {
	        String userEmail = currentUser.get().getEmail();
	        
	        // Check if the user is authorized
	        if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
	            try {
	                // Call the service to delete the puja
	                pujaService.deletePujaById(pujaId); // Use instance method call
	                return ResponseEntity.noContent().build(); // Responds with 204 No Content
	            } catch (RuntimeException e) {
	                return ResponseEntity.notFound().build(); // Responds with 404 Not Found if puja doesn't exist
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                                 .body("Forbidden: You do not have permission to delete this puja"); // Forbidden message
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                             .body("Forbidden: User not found or unauthorized"); // Forbidden if user is not found
	    }
	}

}
