package com.tsb.basicbanking.app.repository;

import com.tsb.basicbanking.app.model.Account;
import com.tsb.basicbanking.app.model.Transaction;
import com.tsb.basicbanking.app.model.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, TransactionId> {

    Optional<List<Transaction>> findByAccount(Account account);
}
