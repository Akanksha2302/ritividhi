package com.dev.ritividhi.services;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

import com.dev.ritividhi.models.Address;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.repository.AddressRepository;
import com.dev.ritividhi.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final AddressRepository addressRepository;
	private final FirebaseAuthService firebaseAuthService;

	public UserService(UserRepository userRepository, AddressRepository addressRepository,
			FirebaseAuthService firebaseAuthService) {
		this.userRepository = userRepository;
		this.addressRepository = addressRepository;
		this.firebaseAuthService = firebaseAuthService;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getCurrentUser(String token) {
		String emailOrPhone = null;
		try {
			if (firebaseAuthService.getUserEmail(token) != null) {
				emailOrPhone = firebaseAuthService.getUserEmail(token);
			} else if (firebaseAuthService.getUserPhoneNumber(token) != null) {
				emailOrPhone = firebaseAuthService.getUserPhoneNumber(token).substring(3);
			}
		} catch (FirebaseAuthException e) {
			throw new RuntimeException(e);
		}
		if (emailOrPhone != null) {
			// Search for the user using email or phone number
			Optional<User> user = userRepository.findByEmailOrPhone(emailOrPhone, emailOrPhone);
			return user;
		}
		return Optional.empty();
	}

	public boolean validateUser(User user, String token) {

		String emailOrPhone = null;
		try {
			if (firebaseAuthService.getUserEmail(token) != null) {
				emailOrPhone = firebaseAuthService.getUserEmail(token);
				if (!user.getEmail().equals(emailOrPhone)) {
					return false;
				}
			} else if (firebaseAuthService.getUserPhoneNumber(token) != null) {
				emailOrPhone = firebaseAuthService.getUserPhoneNumber(token).substring(3);
				if (!user.getPhone().equals(emailOrPhone)) {
					return false;
				}
			}
		} catch (FirebaseAuthException e) {
			throw new RuntimeException(e);
		}

		return true;

	}

	public User createUser(User user) {
		Address address = user.getAddress();
		if (address != null) {
			address = addressRepository.save(address);
			user.setAddress(address);
		}
		return userRepository.save(user);
	}

	public User updateUser(String userId, User user) {
		Optional<Address> address = Optional.ofNullable(user.getAddress());
		address.ifPresent(addressRepository::save);
		user.setUserId(userId);
		return userRepository.save(user);
	}

	public boolean isEmailExists(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	public void deleteUser(String userId) {
		userRepository.findById(userId).ifPresent(user -> {
			if (user.getAddress() != null) {
				addressRepository.deleteById(user.getAddress().getId());
			}
			userRepository.deleteById(userId);
		});
	}

	public Optional<User> getUserById(String userId) {
		Optional<User> user = userRepository.findById(userId);
		return user;
	}
}