package com.getmygraphicscard.identityservice.controller;

import com.getmygraphicscard.identityservice.dto.LoginRequest;
import com.getmygraphicscard.identityservice.dto.UserDto;
import com.getmygraphicscard.identityservice.security.RsaKeyProperties;
import com.getmygraphicscard.identityservice.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final RsaKeyProperties jwtConfigProperties;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> userSignUp(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = authService.saveUser(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getEmail()).toUri();
        return ResponseEntity.created(location).body("New User Registered");
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Token requested for user: '{}'", loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String token = authService.generateToken(authentication);
        log.info("Token granted: {}", token);
        log.info("Public key = " + jwtConfigProperties.publicKey().toString());
        log.info("Private key = " +  jwtConfigProperties.privateKey().toString());
        return ResponseEntity.ok(token);
    }


}
