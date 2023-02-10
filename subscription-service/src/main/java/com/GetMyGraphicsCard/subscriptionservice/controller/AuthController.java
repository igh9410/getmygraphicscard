package com.GetMyGraphicsCard.subscriptionservice.controller;

import com.GetMyGraphicsCard.subscriptionservice.dto.LoginRequest;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionDto;
import com.GetMyGraphicsCard.subscriptionservice.service.AuthServiceImpl;
import com.GetMyGraphicsCard.subscriptionservice.service.SubscriptionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthServiceImpl authService;
    private final SubscriptionServiceImpl subscriptionService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<SubscriptionDto> makeSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok().body(subscriptionService.makeSubscription(subscriptionDto));
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        log.debug("Token requested for user: '{}'", loginRequest.getEmail());
        log.info("Token requested for user: '{}'", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String token = authService.generateToken(authentication);
        log.debug("Token granted: {}", token);
        log.info("Name = {}, Authority = {}",authentication.getName(), authentication.getAuthorities());
        return token;
    }



    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/secure")
    public String secure() {
        return "This is secured!";
    }
}
