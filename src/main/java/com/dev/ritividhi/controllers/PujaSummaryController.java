package com.dev.ritividhi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dev.ritividhi.models.Notification;
import com.dev.ritividhi.models.Order;
import com.dev.ritividhi.models.PujaSummary;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.PujaSummaryService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/pujaSummary")
public class PujaSummaryController {

	@Autowired
	private PujaSummaryService service;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@GetMapping
	public ResponseEntity<List<PujaSummary>> getAllPujas() {
		List<PujaSummary> pujas = service.getAllPujas();
		return new ResponseEntity<>(pujas, HttpStatus.OK);
	}

	@GetMapping("/{pujaId}")
	public ResponseEntity<List<PujaSummary>> getPujaSummaryByPujaId(@PathVariable String pujaId) {
		List<PujaSummary> results = service.findByPujaId(pujaId);
		return results.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(results);
	}

	@PostMapping
	public ResponseEntity<?> createPuja(@RequestBody PujaSummary pujaSummary,
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
				PujaSummary createdPuja = service.savePuja(pujaSummary);
				return new ResponseEntity<>(createdPuja, HttpStatus.CREATED);
			} else {
				return ResponseEntity.status(403).body("Forbidden: You do not have permission to access this resource"); // Forbidden
																															// with
																															// message
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: User not found or unauthorized"); // Forbidden if user not found
		}
	}
//    @PutMapping("/{pujaId}")
//    public ResponseEntity<PujaSummary> updatePuja(
//            @PathVariable String pujaId, @RequestBody PujaSummary pujaSummary) {
//        PujaSummary updatedPuja = service.updatePuja(pujaId, pujaSummary);
//        return updatedPuja != null
//                ? new ResponseEntity<>(updatedPuja, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

	@DeleteMapping("/{pujaId}")
	public ResponseEntity<String> deletePuja(@PathVariable String pujaId,
	        @RequestHeader(name = "Authorization") String bearerToken) {

	    String token = CommonUtils.extractBearerToken(bearerToken);
	    if (token == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
	    }

	    Optional<User> currentUser = userService.getCurrentUser(token);
	    if (currentUser.isPresent()) {
	        String userEmail = currentUser.get().getEmail();

	        // Check if the user is authorized
	        if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
	            boolean deleted = service.deletePuja(pujaId);
	            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Puja not found");
	        } else {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authorized to delete pujas");
	        }
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
	    }
	}

}