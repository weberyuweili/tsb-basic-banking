package com.tsb.basicbanking.app.repository;

import com.tsb.basicbanking.app.model.Account;
import com.tsb.basicbanking.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>
{
    Optional<List<Account>> findByCustomer(Customer customer);
}
