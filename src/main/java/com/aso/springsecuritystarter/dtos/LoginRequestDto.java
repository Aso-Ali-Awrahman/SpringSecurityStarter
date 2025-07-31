package com.aso.springsecuritystarter.dtos;

public record LoginRequestDto(
        String email,
        String password
) {
}
