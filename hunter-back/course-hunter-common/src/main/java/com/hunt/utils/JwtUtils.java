package com.hunt.utils;

import com.hunt.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${Auth.ClientId.secret}")
    private String SECRET_KEY;

    @Value("${Auth.ClientId.expiration}")
    private long EXPIRATION_TIME;

    private SecretKey getSigningKey(){
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("email", user.getEmail());

        String jwt = Jwts.builder()
                .claims(claims)
                .subject(user.getGoogleId())
                .issuedAt(new Date())
                .expiration(new Date((System.currentTimeMillis()) + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
        return jwt;
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
