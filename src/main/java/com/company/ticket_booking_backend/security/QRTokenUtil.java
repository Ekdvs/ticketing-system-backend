package com.company.ticket_booking_backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class QRTokenUtil {

    private static final String SECRET = "my-super-secret-key-my-super-secret-key"; // минимум 32 chars

    private static final Key KEY = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    public static String generateToken(String bookingId, String userId) {
        return Jwts.builder()
                .claim("bookingId", bookingId)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
                .signWith(KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}