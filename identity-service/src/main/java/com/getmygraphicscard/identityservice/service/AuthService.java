package com.getmygraphicscard.identityservice.service;

import com.getmygraphicscard.identityservice.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public String generateToken(Authentication authentication);

    public boolean addTokenToBlackList(String token);

    UserDto saveUser(UserDto userDto);

}
