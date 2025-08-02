package com.aso.springsecuritystarter.controllers;

import com.aso.springsecuritystarter.dtos.CreateUserDto;
import com.aso.springsecuritystarter.dtos.JwtResponseDto;
import com.aso.springsecuritystarter.dtos.LoginRequestDto;
import com.aso.springsecuritystarter.entities.Role;
import com.aso.springsecuritystarter.entities.User;
import com.aso.springsecuritystarter.repositories.UserRepository;
import com.aso.springsecuritystarter.services.JwtService;
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

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), // principle
                        request.password() // credentials
                )
        );

        var user = userRepository.findByEmail(request.email()).orElse(null);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponseDto(token));
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



    // just to say 401 instead of 403
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
