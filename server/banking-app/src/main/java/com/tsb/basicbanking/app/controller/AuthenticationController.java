package com.tsb.basicbanking.app.controller;

import com.tsb.basicbanking.app.component.JwtUtil;
import com.tsb.basicbanking.app.dto.JwtResponse;
import com.tsb.basicbanking.app.dto.LoginRequest;
import com.tsb.basicbanking.app.model.Customer;
import com.tsb.basicbanking.app.service.BankingService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private BankingService bankingService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                        HttpServletResponse response)
    {
        // Access email and password from the loginRequest
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(email);
        var customer = bankingService.login(email, password);

        var token = JwtUtil.generateToken(email);

        return ResponseEntity.ok(new JwtResponse(token, customer.getId()));
    }
}
