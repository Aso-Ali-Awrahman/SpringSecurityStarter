package com.aso.springsecuritystarter.schedulers;

import com.aso.springsecuritystarter.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TokenScheduler {
    private final TokenRepository tokenRepository;

    public TokenScheduler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 7000)
    public void deleteExpiredTokens() {
        System.out.println("Scheduler is running");
        var tokens = tokenRepository.findAll();
        for  (var token : tokens) {
            System.out.println(token.getId());
        }

        tokenRepository.deleteByExpirationLessThanEqual(Instant.now().getEpochSecond()*1000);
    }

}
