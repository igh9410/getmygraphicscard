package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.entity.Root;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WebClientService {
    public Mono<Root> requestGraphicsCardInfo(String title);
    public void addGraphicsCardToDB(Mono<Root> graphicsCard, String chipset);



}
