package com.GetMyGraphicsCard.productservice.service;


import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.entity.Root;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
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

    private final KafkaTemplate<String, String> kafkaTemplate;


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
            System.out.println("Product Id = " + i.getProductId());
            if (itemRepository.existsById(i.getProductId())) {
                Item comparison = itemRepository.findById(i.getProductId()).orElseThrow(() -> new RuntimeException("Item does not exist"));
                if (i.getLprice() <= comparison.getLprice()) {
                    kafkaTemplate.send("alertTopic", "The lowest price information for product Id " + i.getProductId() + " has been updated");
                    log.info("Sending price information message to Kafka..");
                }
            }
        }

        log.info("Saving {} data to DB", chipset);
        itemRepository.saveAll(addedItems);
    }



}
