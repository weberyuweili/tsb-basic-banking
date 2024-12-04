package com.tsb.basicbanking.app.service;

import com.tsb.basicbanking.app.model.Customer;
import com.tsb.basicbanking.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankingService {

    private final CustomerRepository customerRepository;

    public BankingService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer Login(String email, String password) {
        var customer = customerRepository.findByEmail(email);
        return customer.orElse(null);
    }
}
