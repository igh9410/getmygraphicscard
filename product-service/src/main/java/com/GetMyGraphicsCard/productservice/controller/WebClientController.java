package com.GetMyGraphicsCard.productservice.controller;

import com.GetMyGraphicsCard.productservice.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class WebClientController {

    private WebClientService webClientService;

    @Autowired
    public WebClientController(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @GetMapping("/products")
    public Mono<String> listProducts(){

        return webClientService.getProducts();
    }
}
