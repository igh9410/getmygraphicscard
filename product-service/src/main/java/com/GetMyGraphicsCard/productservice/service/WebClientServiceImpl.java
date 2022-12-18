package com.GetMyGraphicsCard.productservice.service;


import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.entity.Root;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WebClientServiceImpl implements WebClientService {

    private final WebClient webClient;
    private final ItemRepository itemRepository;


    @Autowired
    public WebClientServiceImpl(ItemRepository itemRepository) {
        this.webClient =  WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/search/shop.json")
                .defaultHeader("X-Naver-Client-Id", "LN0TxgVzwBlIVpnW0QGb")
                .defaultHeader("X-Naver-Client-Secret", "_9ZdxXu6KD")
                .build();
        this.itemRepository = itemRepository;
    }

    // request Graphics Card Info via Naver API
    public Mono<Root> requestGraphicsCardInfo(String title) {
        Mono<Root> graphicsCard = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", title)
                        .queryParam("display", 100)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class);
        return graphicsCard;
    }


    @Override
    public void addGraphicsCardToDB(Mono<Root> graphicsCard) {
        Root addedProducts = graphicsCard.block();
        List<Item> addedItems = addedProducts.getItems()
                .parallelStream()
                .map(i -> new Item(i.getTitle().replaceAll("\\<.*?>", ""), i.getLink(), i.getImage(), i.getLprice(), i.getProductId()))
                .collect(Collectors.toList());
        itemRepository.saveAll(addedItems);
    }

    @Override
    public Mono<Root> getProducts() {
        Mono<Root> products = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", "RTX 3060ti")
                        .queryParam("display", 100)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class);
        return products;
    }


}
