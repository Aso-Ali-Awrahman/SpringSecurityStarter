package com.aso.springsecuritystarter.services;

import com.aso.springsecuritystarter.entities.User;
import com.aso.springsecuritystarter.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }





}
