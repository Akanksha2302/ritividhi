package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.OfflinePuja;
import com.dev.ritividhi.models.Package;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.PackageService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/package")
public class PackageController {

	@Autowired
	PackageService packageService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@GetMapping
	public ResponseEntity<List<Package>> getAllPackages() {
		List<Package> packages = packageService.getAllPackages();
		return ResponseEntity.ok(packages);
	}

	@PostMapping
	public ResponseEntity<?> createPackage(@RequestBody Package pack,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		if (token == null) {
			return ResponseEntity.status(401).body("Missing or invalid token"); // Unauthorized with message
		}

		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent() && adminEmailAuthService.isEmailAuthorized(currentUser.get().getEmail())) {
			Package savedPackage = packageService.savePackage(pack);
			return ResponseEntity.ok(savedPackage);
		} else {
			return ResponseEntity.status(403).body("Forbidden: You do not have permission to access this resource"); // Forbidden
																														// with
																														// message
		}
	}

	@DeleteMapping("/{packageId}")
	public ResponseEntity<String> deletePackage(@PathVariable String packageId,
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
					// Call the service to delete the package
					packageService.deletePackage(packageId);
					return ResponseEntity.ok().build(); // Responds with 200 OK
				} catch (RuntimeException e) {
					return ResponseEntity.notFound().build(); // Responds with 404 Not Found if package doesn't exist
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("Forbidden: You do not have permission to delete this package"); // Forbidden message
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