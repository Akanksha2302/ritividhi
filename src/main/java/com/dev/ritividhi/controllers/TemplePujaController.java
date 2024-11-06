//package com.dev.ritividhi.controllers;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.dev.ritividhi.models.TemplePujaDetails;
//import com.dev.ritividhi.repository.TemplePujaRepository;
//import com.dev.ritividhi.services.TemplePujaDetailsService;
//
//
//@RestController
//@RequestMapping("/ritividhi/templePujaList")
//public class TemplePujaController {
//	
//		@Autowired
//	    private TemplePujaDetailsService templePujaDetailsService;
//
//	    @PostMapping
//	    public ResponseEntity<TemplePujaDetails> createTemplePuja(@RequestBody TemplePujaDetails puja) {
//	        TemplePujaDetails createdPuja = templePujaDetailsService.createTemplePuja(puja);
//	        return ResponseEntity.ok(createdPuja);
//	    }
//
//	    @GetMapping
//	    public ResponseEntity<List<TemplePujaDetails>> getAllTemplePujas() {
//	        List<TemplePujaDetails> pujas = templePujaDetailsService.getAllTemplePujas();
//	        return ResponseEntity.ok(pujas);
//	    }
//
//	    @GetMapping("/{id}")
//	    public ResponseEntity<TemplePujaDetails> getTemplePujaById(@PathVariable String id) {
//	        TemplePujaDetails puja = templePujaDetailsService.getTemplePujaById(id);
//	        return puja != null ? ResponseEntity.ok(puja) : ResponseEntity.notFound().build();
//	    }
//	    
////	    @DeleteMapping("/{pujaId}")
////	    public ResponseEntity<Void> deleteTemplePujaById(@PathVariable String pujaId) {
////	        if (templePujaRepository.existsById(pujaId)) {
////	            templePujaRepository.deleteByTemplePujaId(pujaId); // Delete the TemplePuja by its ID
////	            return ResponseEntity.noContent().build(); // Return 204 No Content on success
////	        } else {
////	            return ResponseEntity.notFound().build(); // Return 404 Not Found if not found
////	        }
////	    }
////	
////	    @DeleteMapping("/{templeId}")
////	    public ResponseEntity<String> deleteTempleById(@PathVariable String templeId) {
////	        boolean deleted = templePujaService.deleteByTemplePujaId(templeId);
////	        if (deleted) {
////	            return new ResponseEntity<>("Temple Puja deleted successfully.", HttpStatus.OK);
////	        } else {
////	            return new ResponseEntity<>("Temple Puja not found.", HttpStatus.NOT_FOUND);
////	        }
//	    }
//	---------------------------------------------xxxxxxxxxxxxxxxxxxxxxxxx-------------------------------------

package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.Puja;
import com.dev.ritividhi.models.TemplePuja;
import com.dev.ritividhi.models.TemplePujaDetails;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.AdminEmailAuthService;
import com.dev.ritividhi.services.TemplePujaService;
import com.dev.ritividhi.services.UserService;
import com.dev.ritividhi.utils.CommonUtils;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/temple-puja")
public class TemplePujaController {


	@Autowired
	private TemplePujaService templePujaService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminEmailAuthService adminEmailAuthService;

	@PostMapping
	public ResponseEntity<?> createTemplePuja(@RequestBody TemplePuja templePuja,
			@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		if (token == null) {
			return ResponseEntity.status(401).body("Missing or invalid token"); // Unauthorized with message
		}

		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent() && adminEmailAuthService.isEmailAuthorized(currentUser.get().getEmail())) {
			TemplePuja createdTemplePuja = templePujaService.saveTemple(templePuja);
			return ResponseEntity.status(201).body(createdTemplePuja);
		} else {
			return ResponseEntity.status(403).body("Forbidden: You do not have permission to access this resource"); // Forbidden with message
		}
	}

	@GetMapping
	public ResponseEntity<List<TemplePuja>> getAllTemplePujas() {
		List<TemplePuja> templePujas = templePujaService.getAllTemplePujas();
		return ResponseEntity.ok(templePujas);
	}

	@GetMapping("/{templeId}")
	public ResponseEntity<TemplePuja> getTemplePujaById(@PathVariable String templeId) {
		Optional<TemplePuja> templePuja = templePujaService.getTemplePujaById(templeId);

		return templePuja.map(tp -> ResponseEntity.ok(tp)) // If present, return 200 OK
				.orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 NOT FOUND
	}

	// Added a method to fetch Puja details based on temple ID-fetch all puja
	// details linked with a particular temple
	@GetMapping("/{templeId}/pujas")
	public ResponseEntity<List<TemplePujaDetails>> getPujasByTempleId(@PathVariable String templeId) {
		Optional<TemplePuja> templePuja = templePujaService.getTemplePujaById(templeId);

		return templePuja.map(tp -> ResponseEntity.ok(tp.getPujas())) // Assuming tp.getPujas() returns
																		// List<TemplePujaDetails>
				.orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 NOT FOUND
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
		            	templePujaService.deletePujaById(pujaId);
		                return ResponseEntity.ok().build(); // Responds with 200 OK
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


//	 @Autowired
//	    private TemplePujaService templePujaService;
//
//	    @PostMapping
//	    public ResponseEntity<TemplePuja> createTemplePuja(@RequestBody TemplePuja templePuja) {
//	        TemplePuja createdTemplePuja = templePujaService.saveTemple(templePuja);
//	        return ResponseEntity.status(201).body(createdTemplePuja);
//	    }
//
//	    @GetMapping
//	    public ResponseEntity<List<TemplePuja>> getAllTemplePujas() {
//	        List<TemplePuja> templePujas = templePujaService.getAllTemplePujas();
//	        return ResponseEntity.ok(templePujas);
//	    }
//	    
//	    @GetMapping("/{templeId}")
//	    public ResponseEntity<TemplePuja> getTemplePujaById(@PathVariable String templeId) {
//	        Optional<TemplePuja> templePuja = templePujaService.getTemplePujaById(templeId);
//	        
//	        return templePuja.map(tp -> ResponseEntity.ok(tp)) // If present, return 200 OK
//	                         .orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 NOT FOUND
//	    }
//	    
//	    //Added a method to fetch Puja details based on temple ID-fetch all puja details linked with a particular temple
//	    @GetMapping("/{templeId}/pujas")
//	    public ResponseEntity<List<TemplePujaDetails>> getPujasByTempleId(@PathVariable String templeId) {
//	        Optional<TemplePuja> templePuja = templePujaService.getTemplePujaById(templeId);
//	        
//	        return templePuja.map(tp -> ResponseEntity.ok(tp.getPujas())) // Assuming tp.getPujas() returns List<TemplePujaDetails>
//	                          .orElseGet(() -> ResponseEntity.notFound().build()); // If not present, return 404 NOT FOUND
//	    }

}
