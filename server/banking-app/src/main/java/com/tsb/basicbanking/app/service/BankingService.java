package com.tsb.basicbanking.app.service;

import com.tsb.basicbanking.app.dto.AccountDto;
import com.tsb.basicbanking.app.dto.TransferRequest;
import com.tsb.basicbanking.app.model.Customer;
import com.tsb.basicbanking.app.model.Transaction;
import com.tsb.basicbanking.app.repository.AccountRepository;
import com.tsb.basicbanking.app.repository.CustomerRepository;
import com.tsb.basicbanking.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PasswordResetService passwordResetService;

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

    public void requestResetPassword(String email)
    {
        // Verify user exists
        Optional<Customer> user = customerRepository.findByEmail(email);

        if (user.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Generate secure token and OTP
        String resetToken = UUID.randomUUID().toString();
        String otp = String.format("%06d", new SecureRandom().nextInt(999999));

        passwordResetService.saveResetDetails(user.get().getId(), resetToken, otp);

        // Send SMS: Since sms service is not integrated print OTP out to console
        System.out.println("Your OTP is: " + otp);

        // Send email: Since email service is not integrated print URL out to console
        System.out.println("Url to reset: http://localhost:4200/reset-password?token=" + resetToken);
    }

    public void updateUserPassword(String email, String newPassword)
    {
        var userOptional = customerRepository.findByEmail(email);

        if (userOptional.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        var user = userOptional.get();
        user.setPassword(newPassword);

        customerRepository.save(user);

        passwordResetService.removeOtpAndToken(user.getId());
    }
}
