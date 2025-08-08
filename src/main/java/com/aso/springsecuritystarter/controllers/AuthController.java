package com.aso.springsecuritystarter.controllers;

import com.aso.springsecuritystarter.config.JwtConfig;
import com.aso.springsecuritystarter.dtos.CreateUserDto;
import com.aso.springsecuritystarter.dtos.JwtResponseDto;
import com.aso.springsecuritystarter.dtos.LoginRequestDto;
import com.aso.springsecuritystarter.entities.Role;
import com.aso.springsecuritystarter.entities.User;
import com.aso.springsecuritystarter.repositories.UserRepository;
import com.aso.springsecuritystarter.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(
            @RequestBody LoginRequestDto request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), // principle
                        request.password() // credentials
                )
        );

        var user = userRepository.findByEmail(request.email()).orElse(null);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setPath("/auth/refresh");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new JwtResponseDto(accessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CreateUserDto userDto) {

        if (userRepository.findByEmail(userDto.email()).isPresent())
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Email already exists.")
            );

        Role role = Role.CUSTOMER;
        if (userDto.isAdmin())
            role = Role.ADMIN;

        var user = new User(
                0,
                userDto.name(),
                userDto.email(),
                passwordEncoder.encode(userDto.password()),
                role
        );

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refreshToken(@CookieValue(value = "refreshToken") String refreshToken) {
        if (!jwtService.validateToken(refreshToken, JwtService.TokenType.REFRESH))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // check if it is in the black list token.
        var email = jwtService.getEmailFromToken(refreshToken);
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponseDto(accessToken));
    }



    // just to say 401 instead of 403
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
