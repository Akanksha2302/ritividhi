package com.dev.ritividhi.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class AdminEmailAuthService {
	// Code to provide access to particular list of emailID
	private static final Set<String> ADMIN_AUTHORIZED_EMAILS = new HashSet<>(
			Set.of("fernandespearla@gmail.com", "akankshashukla34056@gmail.com", "preetanshu0009@gmail.com"));

	public boolean isEmailAuthorized(String email) {
		return ADMIN_AUTHORIZED_EMAILS.contains(email);
	}

}
