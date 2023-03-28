package com.getmygraphicscard.subscriptionservice.service;

import org.springframework.security.core.Authentication;

public interface AuthService {

    public String generateToken(Authentication authentication);

}
