package com.aso.springsecuritystarter.services;

import com.aso.springsecuritystarter.config.JwtConfig;
import com.aso.springsecuritystarter.entities.Role;
import com.aso.springsecuritystarter.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(User user) {
        // token payload must be small
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * jwtConfig.getAccessTokenExpiration()))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    // validating token
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            // check for expiration if it is expired return false
            return claims.getExpiration().after(new Date());
        }
        catch (JwtException ex) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public Role getRoleFromToken(String token) {
        return Role.valueOf(getClaimsFromToken(token).get("role").toString());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
