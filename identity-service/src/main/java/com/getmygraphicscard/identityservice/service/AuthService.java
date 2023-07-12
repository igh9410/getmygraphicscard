package com.getmygraphicscard.identityservice.service;

import com.getmygraphicscard.identityservice.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public String generateToken(Authentication authentication);

    UserDto saveUser(UserDto userDto);

}
