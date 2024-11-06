package com.dev.ritividhi.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthService {

    public void validateToken(String token) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();
        System.out.println("Extracted user: " + uid + " - " + email );
    }

    public String getUserEmail(String token) throws FirebaseAuthException {
        // System.out.println("token is " + token);
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        System.out.println("user email is: " + decodedToken.getEmail());
        return decodedToken.getEmail();
    }

	public String getUserPhoneNumber(String token) throws FirebaseAuthException{
		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
		
		return (String) decodedToken.getClaims().get("phone_number");
		
		
		// TODO Auto-generated method stub
	}



}
