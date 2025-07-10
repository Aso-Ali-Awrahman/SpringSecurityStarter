package com.aso.springsecuritystarter.dtos;

public record CreateBookDto(
        String title,
        double price,
        boolean isVip
) {
}
