package com.example.FinanceDataBackend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.FinanceDataBackend.entity.User;

@Component
public class JwtUtil {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // generates secure 256-bit key
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hour

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return (String) Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("role");
    }
}