package com.example.sphere.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
@Service
public class UrlGenerator {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.lifetime}")
    private Duration jwtExpirationMs;


    public String generateTemporaryUrl(String userId, String key, String format, String category) {
        long now = System.currentTimeMillis();
        Date expiryDate = new Date(now + jwtExpirationMs.toMillis());
        String token = Jwts.builder()
                .setSubject(userId)
                .claim("key", key)
                .claim("format", format)
                .claim("category", category)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return "http://localhost:3000/files/temp/" + token;
    }
}