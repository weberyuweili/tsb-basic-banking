package com.tsb.basicbanking.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsb.basicbanking.app.service.BankingService;

@RestController
@RequestMapping("/customer")
public class CustomersController {

    @Autowired
    private BankingService bankingService;


}
