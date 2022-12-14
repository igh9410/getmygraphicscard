package com.GetMyGraphicsCard.productservice.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class WebClientController {

    private WebClient webClient;

    @GetMapping("/products")
    public Mono<String> getProducts(){
        webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/search/shop.json?query=RTX 3060ti&display=100")
                .defaultHeader("X-Naver-Client-Id", "LN0TxgVzwBlIVpnW0QGb")
                .defaultHeader("X-Naver-Client-Secret", "_9ZdxXu6KD")
                .build();

        return webClient.get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
