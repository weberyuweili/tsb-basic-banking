package com.tsb.basicbanking.app.controller;

import com.tsb.basicbanking.app.component.JwtUtil;
import com.tsb.basicbanking.app.dto.*;
import com.tsb.basicbanking.app.service.BankingService;
import com.tsb.basicbanking.app.service.PasswordResetService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthenticationController
{
    @Autowired
    private BankingService bankingService;

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                        HttpServletResponse response)
    {
        // Access email and password from the loginRequest
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        var customer = bankingService.login(email, password);

        if (customer == null || !customer.getPassword().equals(password))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        var token = JwtUtil.generateToken(email);

        return ResponseEntity.ok(new JwtResponse(token, customer.getId()));
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest request)
    {
        bankingService.requestResetPassword(request.getEmail());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset/verify")
    public ResponseEntity<?> verifyOtp(@RequestBody PasswordResetVerifyRequest request)
    {
        var isVerified = passwordResetService.verifyOtpAndToken(request.getEmail(), request.getOtp(), request.getToken());

        if (!isVerified)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid OTP or token");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetCompleteRequest request)
    {
        var email = request.getEmail();
        var otp = request.getOtp();
        var token = request.getToken();
        var newPassword = request.getNewPassword();

        var isVerified = passwordResetService.verifyOtpAndToken(email, otp, token);

        if (!isVerified)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid OTP or token");
        }

        bankingService.updateUserPassword(email, newPassword);


        return ResponseEntity.ok().build();
    }
}
