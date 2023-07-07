package com.getmygraphicscard.subscriptionservice.controller;

import com.getmygraphicscard.subscriptionservice.dto.LoginRequest;
import com.getmygraphicscard.subscriptionservice.dto.SubscriptionDto;
import com.getmygraphicscard.subscriptionservice.service.AuthService;
import com.getmygraphicscard.subscriptionservice.service.SubscriptionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final SubscriptionService subscriptionService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<SubscriptionDto> makeSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        return ResponseEntity.ok().body(subscriptionService.makeSubscription(subscriptionDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.debug("Token requested for user: '{}'", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String token = authService.generateToken(authentication);
        log.debug("Token granted: {}", token);
      //  Cookie cookie = new Cookie("token", token);
     //  cookie.setHttpOnly(true);
     //   cookie.setPath("/"); // This will make the cookie available throughout the whole site
    //    response.addCookie(cookie);

        return ResponseEntity.ok(token);
    }


}
