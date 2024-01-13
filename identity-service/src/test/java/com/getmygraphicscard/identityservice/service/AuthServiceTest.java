package com.getmygraphicscard.identityservice.service;

import com.getmygraphicscard.identityservice.dto.UserDto;
import com.getmygraphicscard.identityservice.entity.User;
import com.getmygraphicscard.identityservice.enums.Role;
import com.getmygraphicscard.identityservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Import(AuthServiceImpl.class)
public class AuthServiceTest {


    @Mock
    private JwtEncoder encoder;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private AuthServiceImpl authService;
/*
    @Test
    public void generateTokenTest() {

        User user = new User(1, "test1234", "test5689", "test@gmail.com", USER);
        SecurityUser securityUser = new SecurityUser(user);

        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(securityUser, null, authorities);
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(3, ChronoUnit.HOURS)) // Token is valid for 3 hours
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .claim("scope", user.getRole())
                .build();

      //  String testToken = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        String token = authService.generateToken(auth);
        System.out.println(token);


    } */

    @Test
    public void addTokenToBlockListTest() {

        String token = "testToken";

        ValueOperations<String, String> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.hasKey(token)).thenReturn(false);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.setIfAbsent(token, "blacklist", 3, TimeUnit.HOURS)).thenReturn(true);


        // Act
        boolean result = authService.addTokenToBlockList(token);

        // Assert
        assertTrue(result);
        // Verify
        verify(redisTemplate).hasKey(token);
        verify(valueOperations).setIfAbsent(token, "blacklist", 3, TimeUnit.HOURS);
    }

    @Test
    public void saveUserTest() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("password123");

        when(userRepository.existsByUsername(userDto.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDto savedUser = authService.saveUser(userDto);

        // Assert
        assertEquals(userDto.getUsername(), savedUser.getUsername());
        assertEquals(userDto.getEmail(), savedUser.getEmail());
    }

}
