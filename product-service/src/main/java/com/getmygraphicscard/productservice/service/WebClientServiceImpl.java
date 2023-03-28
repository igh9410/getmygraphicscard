package com.getmygraphicscard.productservice.service;


import com.getmygraphicscard.productservice.entity.Item;
import com.getmygraphicscard.productservice.entity.Root;
import com.getmygraphicscard.productservice.event.PriceAlert;
import com.getmygraphicscard.productservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientServiceImpl implements WebClientService {

    private final WebClient webClient;
    private final ItemRepository itemRepository;

    private final KafkaTemplate<String, PriceAlert> kafkaTemplate;


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

        for (Item i: addedItems) {
            if (itemRepository.existsById(i.getProductId())) {
                System.out.println("Product Id = " + i.getProductId());
                Item comparison = itemRepository.findByLink(i.getLink()).orElseThrow(() -> new RuntimeException("Item does not exist"));
                if (i.getLprice() < comparison.getLprice()) { // Check if the lowest price is available
                    kafkaTemplate.send("alertTopic", new PriceAlert(i.getProductId(), i.getTitle(), i.getLink(), i.getLprice()));
                    log.info("Sending price information message to Kafka..");
                }
            }
        }

        log.info("Saving {} data to DB", chipset);
        itemRepository.saveAll(addedItems);
    }



}
