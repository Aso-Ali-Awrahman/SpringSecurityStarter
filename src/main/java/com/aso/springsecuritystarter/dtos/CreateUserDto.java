package com.aso.springsecuritystarter.dtos;

public record CreateUserDto(
        String name,
        String email,
        String password,
        boolean isAdmin
) {
}
