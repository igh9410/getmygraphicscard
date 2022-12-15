package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.entity.Root;
import reactor.core.publisher.Mono;

public interface WebClientService {
    public Mono<Root> getProducts();
    public void addProducts(Mono<Root> products);
}
