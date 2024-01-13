package com.getmygraphicscard.subscriptionservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean isTokenBlocklisted(String token) {
        return redisTemplate.opsForValue().get(token) != null;
    }
}
