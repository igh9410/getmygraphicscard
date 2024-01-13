package com.getmygraphicscard.productservice.service;

import com.getmygraphicscard.productservice.entity.Root;
import reactor.core.publisher.Mono;

public interface WebClientService {
    public Mono<Root> requestGraphicsCardInfo(String title);

    public void addGraphicsCardToDB(Mono<Root> graphicsCard, String chipset);


}
