package com.dev.ritividhi.controllers;

import com.dev.ritividhi.utils.CommonUtils;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dev.ritividhi.models.User;
import com.dev.ritividhi.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ritividhi/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/currentUser")
	public ResponseEntity<User> getCurrentUser(@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		Optional<User> user = userService.getCurrentUser(token);

		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/currentUser")
	public User createUser(@RequestBody User user, @RequestHeader(name = "Authorization") String bearerToken) {

		String token = CommonUtils.extractBearerToken(bearerToken);

		if (userService.validateUser(user, token)) {
			return userService.createUser(user);
		} else
			return (User) ResponseEntity.badRequest();

	}

	@PutMapping("/currentUser")
	public ResponseEntity<User> updateUser(@RequestHeader(name = "Authorization") String bearerToken,
			@RequestBody User user) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent()) {
			String id = currentUser.get().getUserId();
			return ResponseEntity.ok(userService.updateUser(id, user));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/currentUser")
	public ResponseEntity<Void> deleteUser(@RequestHeader(name = "Authorization") String bearerToken) {
		String token = CommonUtils.extractBearerToken(bearerToken);
		Optional<User> currentUser = userService.getCurrentUser(token);
		if (currentUser.isPresent()) {
			String id = currentUser.get().getUserId();
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
