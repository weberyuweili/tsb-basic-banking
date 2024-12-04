package com.tsb.basicbanking.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
public class Account {
    @jakarta.persistence.Id
    private String accountNumber;
    private String name;

    @ManyToOne
    private Customer customer;

    // Getters and Setters
}