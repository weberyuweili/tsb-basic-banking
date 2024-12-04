package com.tsb.basicbanking.app.repository;

import com.tsb.basicbanking.app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, String>
{
    Optional<Customer> findByEmail(String email);
}
