package com.aso.springsecuritystarter.dtos;

import com.aso.springsecuritystarter.entities.Role;

public record UserDto(
        int id,
        String name,
        String email,
        Role role
) {
}
