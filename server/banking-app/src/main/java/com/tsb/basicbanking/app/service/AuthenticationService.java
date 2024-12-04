package com.tsb.basicbanking.app.service;

import com.tsb.basicbanking.app.component.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    JwtUtil jwtUtil;
}
