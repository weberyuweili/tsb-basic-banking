package com.tsb.basicbanking.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class PasswordResetService
{
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final long TOKEN_EXPIRATION_TIME = 3600; // 1 hour in seconds
    private static final long OTP_EXPIRATION_TIME = 600; // 10 minutes in seconds

    public void saveResetDetails(String phoneNumber, String resetToken, String otp) {
        String tokenKey = "RESET_TOKEN:" + phoneNumber;
        String otpKey = "OTP:" + phoneNumber;

        System.out.println(resetToken);

        // Store reset token in Redis
        redisTemplate.opsForValue().set(tokenKey, resetToken, TOKEN_EXPIRATION_TIME, TimeUnit.SECONDS);

        // Store OTP in Redis
        redisTemplate.opsForValue().set(otpKey, otp, OTP_EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    public boolean verifyOtpAndToken(String phoneNumber, String otp, String resetToken) {
        String tokenKey = "RESET_TOKEN:" + phoneNumber;
        String otpKey = "OTP:" + phoneNumber;

        String storedToken = (String) redisTemplate.opsForValue().get(tokenKey);
        String storedOtp = (String) redisTemplate.opsForValue().get(otpKey);

        return resetToken.equals(storedToken) && otp.equals(storedOtp);
    }

    public boolean isValidToken(String phoneNumber, String resetToken) {
        String tokenKey = "RESET_TOKEN:" + phoneNumber;
        String storedToken = (String) redisTemplate.opsForValue().get(tokenKey);

        return resetToken.equals(storedToken);
    }

    public void removeOtpAndToken(String phoneNumber)
    {
        String tokenKey = "RESET_TOKEN:" + phoneNumber;
        String otpKey = "OTP:" + phoneNumber;
        redisTemplate.delete(Arrays.asList(tokenKey, otpKey));
    }
}
