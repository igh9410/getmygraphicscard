package com.GetMyGraphicsCard.subscriptionservice.component;


import com.GetMyGraphicsCard.subscriptionservice.Model.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.util.Date;

@Component
@Service
@RequiredArgsConstructor
public class JWTGenerator {
    @Value("{jwt.secret}")
    private String SECRET;


    public String generateToken(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 60 * 60 * 3);

        String token = Jwts.builder()
                .setSubject(securityUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }



}
