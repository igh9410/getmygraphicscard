package com.GetMyGraphicsCard.productservice.service;

import reactor.core.publisher.Mono;

public interface WebClientService {
    public Mono<String> getProducts();
}
