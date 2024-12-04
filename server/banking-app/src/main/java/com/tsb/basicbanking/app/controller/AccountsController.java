package com.tsb.basicbanking.app.controller;

import com.tsb.basicbanking.app.dto.LoginRequest;
import com.tsb.basicbanking.app.dto.TransferRequest;
import com.tsb.basicbanking.app.model.Account;
import com.tsb.basicbanking.app.model.Customer;
import com.tsb.basicbanking.app.model.Transaction;
import com.tsb.basicbanking.app.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/{accountNumber}/transactions")
    public List<Transaction> getTransactions(@PathVariable String accountNumber)
    {
        List<Transaction> transactions = null;
        try
        {
            transactions = bankingService.getTransactions(accountNumber);
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatusCode.valueOf(503), "Resource not found");
        }
        return transactions;
    }

    @PostMapping("/{accountNumber}/transfer")
    public void transfer(@PathVariable String accountNumber, @RequestBody TransferRequest transferRequest) throws Exception
    {
            bankingService.transfer(accountNumber, transferRequest);
    }
}
