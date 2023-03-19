package com.pabloescobar.pruebatecnica.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final SecretKey JWT_SECRET;    
    private static final long JWT_EXPIRATION = 86400000L * 365; // (ms) (1 año)
    
    public JwtUtil() {
        this.JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    /**
     * Genera un token JWT
     * 
     * @param username
     * @param email
     * @return Token JWT
     */
    public String generateToken(String name, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        // Se define el contenido del token (payload)
        Claims claims = Jwts.claims().setSubject(name);
        claims.put("name", name);
        claims.put("email", email);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET)
                .compact();
    }
}
