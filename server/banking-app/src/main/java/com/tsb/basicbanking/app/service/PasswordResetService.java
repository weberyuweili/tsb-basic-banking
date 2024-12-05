package com.tsb.basicbanking.app.service;

import com.tsb.basicbanking.app.repository.CustomerRepository;
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

    @Autowired
    private CustomerRepository customerRepository;

    private static final long TOKEN_EXPIRATION_TIME = 3600; // 1 hour in seconds
    private static final long OTP_EXPIRATION_TIME = 600; // 10 minutes in seconds

    public void saveResetDetails(String userId, String resetToken, String otp) {
        String tokenKey = "RESET_TOKEN:" + userId;
        String otpKey = "OTP:" + userId;

        System.out.println(resetToken);

        // Store reset token in Redis
        redisTemplate.opsForValue().set(tokenKey, resetToken, TOKEN_EXPIRATION_TIME, TimeUnit.SECONDS);

        // Store OTP in Redis
        redisTemplate.opsForValue().set(otpKey, otp, OTP_EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    public boolean verifyOtpAndToken(String email, String otp, String resetToken) {

        var customer = customerRepository.findByEmail(email);

        if (customer.isEmpty())
        {
            return false;
        }

        String tokenKey = "RESET_TOKEN:" + customer.get().getId();
        String otpKey = "OTP:" + customer.get().getId();

        String storedToken = (String) redisTemplate.opsForValue().get(tokenKey);
        String storedOtp = (String) redisTemplate.opsForValue().get(otpKey);

        return resetToken.equals(storedToken) && otp.equals(storedOtp);
    }

    public void removeOtpAndToken(String userId)
    {
        String tokenKey = "RESET_TOKEN:" + userId;
        String otpKey = "OTP:" + userId;
        redisTemplate.delete(Arrays.asList(tokenKey, otpKey));
    }
}
