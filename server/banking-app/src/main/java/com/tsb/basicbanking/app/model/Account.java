package com.tsb.basicbanking.app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Account {
    @jakarta.persistence.Id
    private String accountNumber;
    private String name;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double getBalance()
    {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }
}