package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AddonService;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/addons")
public class AddonController {

	@Autowired
	AddonService addonService;

	@Autowired
	UserService emailAuthorizationService;

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@GetMapping
	public ResponseEntity<List<Addon>> getAllAddons() {
		List<Addon> addons = addonService.getAllAddons();
		return ResponseEntity.ok(addons);
	}

	@PostMapping
	public ResponseEntity<?> createAddon(@RequestBody Addon addon,
			@RequestHeader(name = "Authorization") String bearerToken) {

		String token = CommonUtils.extractBearerToken(bearerToken);
		System.out.println(token);
		if (token == null) {
			return ResponseEntity.status(401).body("Missing or invalid token"); // Unauthorized with message
		}

		Optional<User> currentUser = emailAuthorizationService.getCurrentUser(token);
		System.out.println(currentUser);
		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();
			System.out.println(userEmail);
			// Check if the user is authorized
			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				Addon savedAddon = addonService.saveAddon(addon);
				return ResponseEntity.ok(savedAddon);
			} else {
				return ResponseEntity.status(403).body("Forbidden: You do not have permission to access this resource"); // Forbidden
																															// with
			} // message
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized"); // Forbidden
																													// if
																													// user
																													// is
																													// not
																													// found
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAddon(@PathVariable("id") String id,
			@RequestHeader(name = "Authorization") String bearerToken) {

		String token = CommonUtils.extractBearerToken(bearerToken);
		System.out.println(token);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
		}

		Optional<User> currentUser = emailAuthorizationService.getCurrentUser(token);
		System.out.println(currentUser + "Current user");
		if (currentUser.isPresent()) {
			String userEmail = currentUser.get().getEmail();
			System.out.println(userEmail);
			// Check if the user is authorized
			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
				addonService.deleteAddon(id);
				return ResponseEntity.noContent().build(); // Responds with 204 No Content
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to delete addons");
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
		}
	}
}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<String> deleteAddon(@PathVariable("id") String id,
//			@RequestHeader(name = "Authorization") String bearerToken) {
//		String token = CommonUtils.extractBearerToken(bearerToken);
//		if (token == null) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
//		}
//
//		try {
//			Optional<User> currentUser = emailAuthorizationService.getCurrentUser(token);
//			if (currentUser.isPresent()) {
//				String userEmail = currentUser.get().getEmail();
//
//				// Check if the user is authorized
//				if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
//					addonService.deleteAddon(id);
//					return ResponseEntity.noContent().build(); // Responds with 204 No Content
//				} else {
//					return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to delete addons");
//				}
//			} else {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
//			}
//		} catch (FirebaseAuthException e) {
//			// Handle Firebase specific exceptions
//			if (e.getMessage().contains("token has expired")) {
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//						.body("Firebase ID token has expired. Please log in again.");
//			}
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Firebase token.");
//		} catch (Exception e) {
//			// Handle other exceptions
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
//		}
//	}
//}

//    @Autowired
//    AddonService addonService;
    
//    @Autowired
//    UserService userService;
//    
//   
//    @Autowired
//    private CommonUtils commonUtils;

//    @GetMapping
//    public ResponseEntity<List<Addon>> getAllAddons() {
//        List<Addon> addons = addonService.getAllAddons();
//        return ResponseEntity.ok(addons);
//    }

//    
//
//   @PostMapping
//   public ResponseEntity<Addon> createAddon(@RequestBody Addon addon) {
//       Addon savedAddon = addonService.saveAddon(addon);
//       return ResponseEntity.ok(savedAddon);
//   }
//    
//    @PostMapping
//    public ResponseEntity<Addon> createAddon(@RequestHeader("Authorization") String token, @RequestBody Addon addon) {
//        try {
//            // Remove "Bearer " prefix if it exists
//            String idToken = token.startsWith("Bearer ") ? token.substring(7) : token;
//
//            // Verify the token and get the email
//            String email = commonUtils.verifyTokenAndGetEmail(idToken);
//
//            // Check if the email is authorized
//            if (!userService.isEmailAuthorized(email)) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//            }
//
//            // Proceed with saving the addon
//            Addon savedAddon = addonService.saveAddon(addon);
//            return ResponseEntity.ok(savedAddon);
//        } catch (FirebaseAuthException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
