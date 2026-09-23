package com.ecommerce.security;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.*;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
	
	private static final String SECRET = "mySecretKeyForJwtAuthentication12345678901234567890"; // Only your application should know this key.
	
	private final SecretKey  key = Keys.hmacShaKeyFor(SECRET.getBytes()); // Converts the String into a secure signing key.
	
	public String generateToken(String email) {
		return Jwts.builder() // Starts building the token.
				.subject(email) // Stores the logged-in user's email.
				.issuedAt(new Date()) // Stores the token creation time.
				.expiration(new Date(System.currentTimeMillis() + 86400000)) // Token expires after: 24 Hours
				.signWith(key) // Signs the JWT. (Without this signature anyone could modify the token).
				.compact(); // Finally converts everything into (eyJhbGciOiJIUzI1NiJ9...)
	}
	
	public String extractUsername(String token) {

	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload()
	            .getSubject();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		String username = extractUsername(token);
		
		return username.equals(userDetails.getUsername());
	}

}
