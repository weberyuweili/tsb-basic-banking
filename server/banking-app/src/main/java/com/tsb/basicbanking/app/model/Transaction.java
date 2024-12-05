package com.tsb.basicbanking.app.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@IdClass(TransactionId.class)
public class Transaction {

    @Id
    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Timestamp transactionDate;

    public Transaction(){}

    public Transaction(Account account, String description,
           Double amount, Timestamp transactionDate)
    {
        this.account = account;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }
}
