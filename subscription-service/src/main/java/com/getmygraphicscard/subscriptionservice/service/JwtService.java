package com.getmygraphicscard.subscriptionservice.service;

public interface JwtService {
    boolean isTokenBlacklisted(String token);
}
