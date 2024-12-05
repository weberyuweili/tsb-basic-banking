package com.tsb.basicbanking.app.controller;

import com.tsb.basicbanking.app.dto.AccountDto;
import com.tsb.basicbanking.app.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsb.basicbanking.app.service.BankingService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomersController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/{customerId}/accounts")
    public List<AccountDto> getAccounts(@PathVariable String customerId)
    {
        List<AccountDto> accounts = null;
        try
        {
            accounts = bankingService.getAccounts(customerId);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatusCode.valueOf(503), "Resource not found");
        }

        return accounts;
    }
}
