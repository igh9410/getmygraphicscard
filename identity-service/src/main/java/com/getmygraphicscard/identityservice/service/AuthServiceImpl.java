package com.getmygraphicscard.identityservice.service;

import com.getmygraphicscard.identityservice.dto.UserDto;
import com.getmygraphicscard.identityservice.entity.User;
import com.getmygraphicscard.identityservice.enums.Role;
import com.getmygraphicscard.identityservice.model.SecurityUser;
import com.getmygraphicscard.identityservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtEncoder encoder;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public String generateToken(Authentication authentication) {

        String email = ((SecurityUser) authentication.getPrincipal()).getEmail(); // Retrieve the email from the SecurityUser
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(3, ChronoUnit.HOURS)) // Token is valid for 3 hours
                .subject(authentication.getName())
                .claim("email", email)
                .claim("scope", scope)
                .build();
        String token = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        log.info("New Token Generated");
        return token;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("User with same username or email already registered");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        log.info("New user registered into database");

        return userDto;

    }


}
