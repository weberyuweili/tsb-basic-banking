package com.tsb.basicbanking.app.component;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;

@Component
public class JwtUtil {

    private final Key secretKey = Jwts.SIG.HS256.key().build(); // Generate a secret key
    private final long expirationTimeMs = 86400000; // 1 day in milliseconds

    // Generate a JWT token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(secretKey) // Sign with the secret key
                .compact();
    }

    // Validate a JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true; // Token is valid
        } catch (Exception e) {
            return false; // Invalid token
        }
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
