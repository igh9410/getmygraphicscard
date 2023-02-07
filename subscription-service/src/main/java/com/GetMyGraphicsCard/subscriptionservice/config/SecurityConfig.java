package com.GetMyGraphicsCard.subscriptionservice.config;


import com.GetMyGraphicsCard.subscriptionservice.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            // Disable CSRF protection because there's no need to use it when using JWT.

            http.csrf().disable();
            http
                    .authorizeHttpRequests()
                    .requestMatchers("/api/subscriptions/", "/api/auth/login")
                    .permitAll()
                    .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/api/subscriptions/**")
                    .authenticated()
                    .and()
                    .formLogin()
                    .and()
                    .logout()
                    .permitAll();

        //            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


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
