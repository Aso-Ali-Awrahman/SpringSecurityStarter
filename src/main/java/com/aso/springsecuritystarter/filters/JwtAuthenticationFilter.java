package com.aso.springsecuritystarter.filters;

import com.aso.springsecuritystarter.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Invalid Authorization header");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace("Bearer ", "");
        if (!jwtService.validateToken(token)) {
            System.out.println("Invalid token");
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Token is valid");
        // token is valid, create the authentication object
        var authentication = new UsernamePasswordAuthenticationToken(
                jwtService.getEmailFromToken(token),
                null,
                null
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}
