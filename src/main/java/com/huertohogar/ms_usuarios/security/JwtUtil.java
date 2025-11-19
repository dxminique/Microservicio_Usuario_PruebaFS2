package com.huertohogar.ms_usuarios.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;


    public String generateToken(String email, String rol) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("rol", rol); // aquí guardamos el rol

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // subject = email
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(
                        Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }


    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }


    public String getRolFromToken(String token) {
        return getClaims(token).get("rol", String.class);
    }


    public boolean isTokenValid(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.after(new Date());
    }
}
