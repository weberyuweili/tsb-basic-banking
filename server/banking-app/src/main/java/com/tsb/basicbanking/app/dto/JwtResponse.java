package com.tsb.basicbanking.app.dto;

public class JwtResponse {
    private String token;
    private String customerId;

    public JwtResponse(String token, String customerId) {
        this.token = token;
        this.customerId = customerId;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}

