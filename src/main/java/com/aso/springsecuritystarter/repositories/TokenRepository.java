package com.aso.springsecuritystarter.repositories;

import com.aso.springsecuritystarter.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {
    void deleteByExpirationLessThanEqual(Long now);
}
