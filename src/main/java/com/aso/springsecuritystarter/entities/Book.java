package com.aso.springsecuritystarter.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "is_vip", nullable = false)
    private boolean isVip;
}
