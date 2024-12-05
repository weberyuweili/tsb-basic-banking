package com.tsb.basicbanking.app.service;

import com.tsb.basicbanking.app.component.JwtUtil;
import com.tsb.basicbanking.app.dto.AccountDto;
import com.tsb.basicbanking.app.dto.TransferRequest;
import com.tsb.basicbanking.app.model.Customer;
import com.tsb.basicbanking.app.model.Transaction;
import com.tsb.basicbanking.app.repository.AccountRepository;
import com.tsb.basicbanking.app.repository.CustomerRepository;
import com.tsb.basicbanking.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BankingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Customer login(String email, String password)
    {
        var customer = customerRepository.findByEmail(email);
        return customer.orElse(null);
    }

    public List<AccountDto> getAccounts(String customerId) throws Exception
    {
        var customer = customerRepository.findById(customerId);

        if (customer.isEmpty())
        {
            throw new Exception("");
        }

       var accountsByCustomer = accountRepository.findByCustomer(customer.get());

        List<AccountDto> accountDtos = new ArrayList<AccountDto>();

        for (var account : accountsByCustomer)
        {
            accountDtos.add(new AccountDto(
                    account.getAccountNumber(),
                    account.getName(),
                    account.getBalance()
            ));
        }
        return accountDtos;
    }

    public List<Transaction> getTransactions(String accountNumber) throws Exception
    {
        var account = accountRepository.findById(accountNumber);

        if (account.isEmpty())
        {
            throw new Exception("");
        }

        var transactionsByAccount = transactionRepository.findByAccount(account.get());

        return transactionsByAccount.orElse(new ArrayList<>());
    }

    public void transfer(String accountNumber, TransferRequest request) throws Exception
    {
        if (accountNumber == request.getToAccountNumber())
        {
            return;
        }

        if (request.getAmount() <= 0)
        {
            return;
        }

        var fromAccount = accountRepository.findById(accountNumber);

        if (fromAccount.isEmpty())
        {
            return;
        }

        var toAccount = accountRepository.findById(request.getToAccountNumber());

        if (toAccount.isEmpty())
        {
            return;
        }

        var deductTransaction = new Transaction(fromAccount.get(),
                request.getReference(), -request.getAmount(), new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(deductTransaction);

        var creditTransaction = new Transaction(toAccount.get(),
                request.getReference(), request.getAmount(), new Timestamp(System.currentTimeMillis()));
        transactionRepository.save(creditTransaction);
    }
}
