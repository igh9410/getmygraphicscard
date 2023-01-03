package com.GetMyGraphicsCard.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            http.csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/api/items/**").permitAll()
                .and()
                .authorizeExchange()
                .pathMatchers("/api/subscriptions/**").authenticated()
                    .and()
                    .oauth2Login()
                    .and()
                    .logout().logoutUrl("http://localhost:3000");
        return http.build();
    }
}
