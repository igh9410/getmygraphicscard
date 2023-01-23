package com.GetMyGraphicsCard.subscriptionservice.config;


import com.GetMyGraphicsCard.subscriptionservice.component.JWTAuthEntryPoint;
import com.GetMyGraphicsCard.subscriptionservice.component.JwtFilter;
import com.GetMyGraphicsCard.subscriptionservice.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthEntryPoint entryPoint;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtFilter jwtFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            // Disable CSRF protection because there's no need to use it when using JWT.

            http.csrf().disable()
                    .exceptionHandling()
                    .authenticationEntryPoint(entryPoint)
                    .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/subscriptions/**", "/api/login")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
