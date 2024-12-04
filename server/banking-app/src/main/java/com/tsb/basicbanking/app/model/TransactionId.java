package com.tsb.basicbanking.app.model;

import jakarta.persistence.*;

import java.io.Serializable;

public class TransactionId implements Serializable {
    private Account account;

    private Long id;

    // Constructors, Getters, Setters, Equals, and HashCode

    public TransactionId() {}

    public TransactionId(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionId that = (TransactionId) o;

        if (!account.equals(that.account)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = account.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }
}
