package com.getmygraphicscard.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/search/shop.json")
                .defaultHeader("X-Naver-Client-Id", "LN0TxgVzwBlIVpnW0QGb")
                .defaultHeader("X-Naver-Client-Secret", "_9ZdxXu6KD")
                .build();
    }
}
