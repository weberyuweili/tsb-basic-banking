package com.tsb.basicbanking.app.controller;

import com.tsb.basicbanking.app.dto.LoginRequest;
import com.tsb.basicbanking.app.model.Customer;
import com.tsb.basicbanking.app.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/login")
    public Customer login(@RequestBody LoginRequest loginRequest)
    {
        // Access email and password from the loginRequest
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(email);
        var customer = bankingService.Login(email, password);
        return customer;
    }
}
