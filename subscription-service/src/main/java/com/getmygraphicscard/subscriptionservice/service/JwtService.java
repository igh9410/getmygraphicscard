package com.getmygraphicscard.subscriptionservice.service;

public interface JwtService {
    boolean isTokenBlocklisted(String token);
}
