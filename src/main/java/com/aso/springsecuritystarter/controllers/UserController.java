package com.aso.springsecuritystarter.controllers;

import com.aso.springsecuritystarter.dtos.UserDto;
import com.aso.springsecuritystarter.mappers.UserMapper;
import com.aso.springsecuritystarter.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getUsers();

        return ResponseEntity.ok(
                users.stream()
                .map(userMapper::toDto)
                .toList()
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMyInfo() {
        // after JWT implementation, using the principle object to get the info
        return ResponseEntity.ok(null);
    }

}
