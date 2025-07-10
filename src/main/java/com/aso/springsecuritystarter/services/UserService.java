package com.aso.springsecuritystarter.services;

import com.aso.springsecuritystarter.dtos.UserDto;
import com.aso.springsecuritystarter.entities.User;
import com.aso.springsecuritystarter.mappers.UserMapper;
import com.aso.springsecuritystarter.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Optional<User> getUser(int id) {
        return userRepository.findById(id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }





}
