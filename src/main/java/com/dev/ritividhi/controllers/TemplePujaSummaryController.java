//package com.dev.ritividhi.controllers;
//
//import com.dev.ritividhi.models.OnlinePuja;
//import com.dev.ritividhi.models.Puja;
//import com.dev.ritividhi.models.TemplePuja;
//import com.dev.ritividhi.models.TemplePujaDetails;
//import com.dev.ritividhi.models.TemplePujaSummary;
//import com.dev.ritividhi.models.User;
//import com.dev.ritividhi.repository.PujaRepository;
//import com.dev.ritividhi.repository.TemplePujaRepository;
//import com.dev.ritividhi.repository.TemplePujaSummaryRepository;
//import com.dev.ritividhi.services.AdminEmailAuthService;
//import com.dev.ritividhi.services.TemplePujaSummaryService;
//import com.dev.ritividhi.services.UserService;
//import com.dev.ritividhi.utils.CommonUtils;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/ritividhi/templePujaSummary")
//public class TemplePujaSummaryController {
//
//	@Autowired
//	private TemplePujaSummaryService service;
//
//	@Autowired
//	UserService userService;
//
//	@Autowired
//	AdminEmailAuthService adminEmailAuthService;
//
//	@Autowired
//	TemplePujaRepository templePujaRepository;
//
//	@Autowired
//	PujaRepository pujaRepository;
//
//	@Autowired
//	TemplePujaSummaryRepository templePujaSummaryRepository;
//
//	@Autowired
//	TemplePujaSummaryService templePujaSummaryService;
//	
////	@PostMapping
////	public ResponseEntity<?> createTemplePujaSummary(@RequestBody TemplePujaSummary summary,
////			@RequestHeader(name = "Authorization") String bearerToken) {
////
////		String token = CommonUtils.extractBearerToken(bearerToken);
////		if (token == null) {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
////		}
////		Optional<User> currentUser = userService.getCurrentUser(token);
////		if (currentUser.isPresent()) {
////			String userEmail = currentUser.get().getEmail();
////			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
////				TemplePujaSummary savedSummary = service.saveTemplePujaSummary(summary);
////				return ResponseEntity.ok(savedSummary);
////			} else {
////				return ResponseEntity.status(HttpStatus.FORBIDDEN)
////						.body("Forbidden: You do not have permission to access this resource");
////			}
////		} else {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
////		}
////	}
//
////	@GetMapping("/{id}")
////	public ResponseEntity<?> getTemplePujaSummary(@PathVariable("id") String id,
////			@RequestHeader(name = "Authorization") String bearerToken) {
////
////		String token = CommonUtils.extractBearerToken(bearerToken);
////		if (token == null) {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
////		}
////
////		Optional<User> currentUser = userService.getCurrentUser(token);
////		if (currentUser.isPresent()) {
////			Optional<TemplePujaSummary> summary = service.getTemplePujaSummary(id);
////			return summary.map(ResponseEntity::ok)
////					.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Temple Puja Summary not found"));
////		} else {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
////		}
////	}
//
////	@GetMapping("/{id}")
////	public ResponseEntity<TemplePujaSummary> getTemplePujaSummary(@PathVariable("id") String id) {
////		return templePujaSummaryRepository.findById(id).map(ResponseEntity::ok)
////				.orElse(ResponseEntity.notFound().build());
////	}
////
////	@PutMapping("/{id}")
////	public ResponseEntity<?> updateTemplePujaSummary(@PathVariable("id") String id,
////			@RequestBody TemplePujaSummary summary, @RequestHeader(name = "Authorization") String bearerToken) {
////
////		String token = CommonUtils.extractBearerToken(bearerToken);
////		if (token == null) {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
////		}
////
////		Optional<User> currentUser = userService.getCurrentUser(token);
////		if (currentUser.isPresent()) {
////			String userEmail = currentUser.get().getEmail();
////			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
////				TemplePujaSummary updatedSummary = service.updateTemplePujaSummary(id, summary);
////				return ResponseEntity.ok(updatedSummary);
////			} else {
////				return ResponseEntity.status(HttpStatus.FORBIDDEN)
////						.body("Forbidden: You do not have permission to access this resource");
////			}
////		} else {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
////		}
////	}
////
////	@DeleteMapping("/{id}")
////	public ResponseEntity<String> deleteTemplePujaSummary(@PathVariable("id") String id,
////			@RequestHeader(name = "Authorization") String bearerToken) {
////
////		String token = CommonUtils.extractBearerToken(bearerToken);
////		if (token == null) {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
////		}
////
////		Optional<User> currentUser = userService.getCurrentUser(token);
////		if (currentUser.isPresent()) {
////			String userEmail = currentUser.get().getEmail();
////			if (adminEmailAuthService.isEmailAuthorized(userEmail)) {
////				service.deleteTemplePujaSummary(id);
////				return ResponseEntity.noContent().build(); // Responds with 204 No Content
////			} else {
////				return ResponseEntity.status(HttpStatus.FORBIDDEN)
////						.body("User is not authorized to delete temple puja summaries");
////			}
////		} else {
////			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token or user not found");
////		}
////	}
////
////	@GetMapping
////	public ResponseEntity<List<TemplePujaSummary>> getAllOnlinePujas() {
////		List<TemplePujaSummary> templePujaSummary = templePujaSummaryRepository.findAll();
////		return ResponseEntity.ok(templePujaSummary);
////	}
////	
//
//	// trial
//
////	@PostMapping
////	public ResponseEntity<TemplePujaSummary> createTemplePujaSummary(@RequestBody TemplePujaSummary summary) {
////		TemplePujaSummary createdSummary = service.saveTemplePujaSummary(summary);
////		return ResponseEntity.status(201).body(createdSummary);
////	}
////
////	@GetMapping
////	public ResponseEntity<List<TemplePujaSummary>> getAllTemplePujaSummaries() {
////		List<TemplePujaSummary> summaries = service.getAllTemplePujaSummaries();
////		return ResponseEntity.ok(summaries);
////	}
////
////	// GET: Retrieve a TemplePujaSummary by ID
////	@GetMapping("/{id}")
////	public ResponseEntity<TemplePujaSummary> getTemplePujaSummary(@PathVariable String id) {
////		Optional<TemplePujaSummary> summary = service.getTemplePujaSummary(id);
////		return summary.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
////	}
////
////	// PUT: Update an existing TemplePujaSummary
////	@PutMapping("/{id}")
////	public ResponseEntity<TemplePujaSummary> updateTemplePujaSummary(@PathVariable String id,
////			@RequestBody TemplePujaSummary summary) {
////		summary.setTempleId(id); // Assuming templeId needs to be set to the path variable id
////		TemplePujaSummary updatedSummary = service.updateTemplePujaSummary(id, summary);
////		return ResponseEntity.ok(updatedSummary);
////	}
////
////	// DELETE: Delete a TemplePujaSummary by ID
////	@DeleteMapping("/{id}")
////	public ResponseEntity<Void> deleteTemplePujaSummary(@PathVariable String id) {
////		service.deleteTemplePujaSummary(id);
////		return ResponseEntity.noContent().build();
////	}
//
//
//////new trialn latest
//	@PostMapping
//    public ResponseEntity<TemplePujaSummary> createTemplePujaSummary(@RequestBody TemplePujaSummary summary) {
//        TemplePujaSummary createdSummary = templePujaSummaryService.createTemplePujaSummary(summary);
//        return new ResponseEntity<>(createdSummary, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<TemplePujaSummary> updateTemplePujaSummary(@PathVariable String id, @RequestBody TemplePujaSummary summary) {
//        TemplePujaSummary updatedSummary = templePujaSummaryService.updateTemplePujaSummary(id, summary);
//        return new ResponseEntity<>(updatedSummary, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTemplePujaSummary(@PathVariable String id) {
//        templePujaSummaryService.deleteTemplePujaSummary(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<TemplePujaSummary> getTemplePujaSummary(@PathVariable String id) {
//        Optional<TemplePujaSummary> summaryOpt = templePujaSummaryService.getTemplePujaSummary(id);
//        return summaryOpt.map(summary -> new ResponseEntity<>(summary, HttpStatus.OK))
//                         .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<TemplePujaSummary>> getAllTemplePujaSummaries() {
//        List<TemplePujaSummary> summaries = templePujaSummaryService.getAllTemplePujaSummaries();
//        return new ResponseEntity<>(summaries, HttpStatus.OK);
//    }
//	
//	
//
//}


package com.dev.ritividhi.controllers;

import com.dev.ritividhi.models.TemplePujaSummary;
import com.dev.ritividhi.services.TemplePujaSummaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ritividhi/templePujaSummaries")
public class TemplePujaSummaryController {

    @Autowired
    private TemplePujaSummaryService summaryService;

    @GetMapping
    public List<TemplePujaSummary> getAllSummaries() {
        return summaryService.getAllTemplePujaSummaries();
    }

    @GetMapping("/{id}")
    public TemplePujaSummary getSummaryById(@PathVariable String id) {
        return summaryService.getSummaryById(id);
    }
}

