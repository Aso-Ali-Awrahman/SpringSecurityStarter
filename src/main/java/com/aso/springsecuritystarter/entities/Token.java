package com.aso.springsecuritystarter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "expiration", nullable = false, updatable = false)
    private Long expiration;
}
