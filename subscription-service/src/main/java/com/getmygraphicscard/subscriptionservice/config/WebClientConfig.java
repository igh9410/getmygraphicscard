package com.getmygraphicscard.subscriptionservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final String authorizationHeader = "Authorization";

    @Bean
    @LoadBalanced
    public WebClient webClient() {
        return WebClient.builder()
             //   .baseUrl("http://product-service/api")
                .baseUrl("http://localhost:5000/api")
                .build();

    }
}
