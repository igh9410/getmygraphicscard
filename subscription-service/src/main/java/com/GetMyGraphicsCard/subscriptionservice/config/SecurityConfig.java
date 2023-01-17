package com.GetMyGraphicsCard.subscriptionservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


            http.csrf().ignoringRequestMatchers("/api/subscriptions/", "/api/subscriptions/**")
                    .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/subscriptions/")
                    .permitAll()
                    .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/subscriptions/**")
                    .permitAll();

            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));



        //
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
