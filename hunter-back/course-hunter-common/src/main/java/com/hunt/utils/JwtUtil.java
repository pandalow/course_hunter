package com.hunt.utils;

import com.hunt.constant.JwtConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "course_advisor_helps_you_choose_your_course_wiser";
    private static final Key JWT_SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * generate jwt token
     * use HS256 algorithm for signature
     *
     * @param claims - keys includes: id, username, isVerified
     * @return jwt token
     */
    public static String createJWT(Map<String, Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Instant issuedAt = Instant.now();
        Instant expirationInstant = issuedAt.plusMillis(JwtConstant.TOKEN_DURATION);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expirationInstant))
                .signWith(JWT_SECRET_KEY,signatureAlgorithm);
        return builder.compact();
    }

    /**
     * Token parser
     *
     * @param token - token to be parsed
     * @return claims
     */
    public static Claims parseJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY) // Set the signing key
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Return the claims
        return claims;
    }

}
