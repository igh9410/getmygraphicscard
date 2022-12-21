package com.GetMyGraphicsCard.productservice.service;


import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.entity.Root;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
        log.info("Requesting {} info", title);
        Mono<Root> graphicsCard = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", title)
                        .queryParam("display", 100)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class)
                .timeout(Duration.ofSeconds(3));
        return graphicsCard;
    }


    @Override
    public void addGraphicsCardToDB(Mono<Root> graphicsCard, String chipset) {
        Root addedProducts = graphicsCard.block();
        List<Item> addedItems = addedProducts.getItems()
                .parallelStream()
                .map(i -> new Item(i.getTitle().replaceAll("\\<.*?>", ""), i.getLink(), i.getImage(), i.getLprice(), i.getProductId()))
                .collect(Collectors.toList());
        log.info("Saving {} data to DB", chipset);
        itemRepository.saveAll(addedItems);
    }

    @Override
    public void addGraphicsCardsToDB(Flux<Root> graphicsCard) {

    }

}
