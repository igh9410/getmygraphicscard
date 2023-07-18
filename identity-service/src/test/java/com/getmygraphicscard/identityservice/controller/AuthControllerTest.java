package com.getmygraphicscard.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getmygraphicscard.identityservice.config.SecurityConfig;
import com.getmygraphicscard.identityservice.dto.LoginRequest;
import com.getmygraphicscard.identityservice.dto.UserDto;
import com.getmygraphicscard.identityservice.entity.User;
import com.getmygraphicscard.identityservice.security.RsaKeyProperties;
import com.getmygraphicscard.identityservice.service.AuthService;
import com.getmygraphicscard.identityservice.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private RsaKeyProperties jwtConfigProperties;


    @Test
    public void userSignUpTest() throws Exception {
        UserDto userDto = new UserDto().builder()
                .username("test1234")
                .email("test1@gmail.com")
                .password("test5689")
                .build();

        when(authService.saveUser(any(UserDto.class))).thenReturn(userDto);


        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("New User Registered"));
    }

    @Test
    public void loginTest() throws Exception {
        String username = "test1234";
        String password = "test5689";
        LoginRequest loginRequest = new LoginRequest(username, password);


        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(username, password));
        when(authService.generateToken(any())).thenReturn("testToken");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("testToken"));
    }

    @Test
    public void logoutTest() throws Exception {

        String testToken = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJldmVyeWEiLCJzY29wZSI6IlVTRVIiLCJpc3MiOiJzZWxmIiwiZXhwIjoxNjg5NjgyMDM1LCJpYXQiOjE2ODk2NzEyMzUsImVtYWlsIjoiaGFsbHdheUBnbWFpbC5jb20ifQ.O9CzqacixrWxogcuHV6P4DZ2X4jaGjx-ggvJ0m_5eskdZcW-1HbcmdulFnkaHi3ZRft83sIXMA14nSEUB0c_wSFV0ydrQonEVoqU4gf0evAmEXXqZtXD0Bv7SjT131TNEDw5LpXKadEWTZbwIc6p9P2F3ccy0pjsnoUXG4Q3IUlLXWhlqFJK5qVpEgMka5NEBfot601zX4GyKJVehNnrwi5jNeFQUUFuUEWgnqR3o32QmafbE4w-aUx61k_Gl3xLEw1ig-_gZwzmtaM7V_2lbYkhlzyk6p7egIU6lnTAsylZ-9m8k4AFYwpfs0ugXjFpJJsbRGUtuaEVKSPeC2xyCQ";
        String bearerToken = "Bearer " + testToken;
        // Define what your AuthService should return when logout is called
        when(authService.addTokenToBlackList(testToken)).thenReturn(true);

        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", bearerToken))
                .andExpect(status().isNoContent());

    }



}
