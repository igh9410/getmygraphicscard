package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.entity.Root;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.MembershipKey;

public interface WebClientService {
    public Mono<Root> requestGraphicsCardInfo(String title);
    public void addGraphicsCardToDB(Mono<Root> graphicsCard);

    public Mono<Root> getProducts();

}
