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

    public String generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    public String generateToken(User user, int expiration) {
        // token payload must be small
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                // claim to specify token type.
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * expiration))
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
