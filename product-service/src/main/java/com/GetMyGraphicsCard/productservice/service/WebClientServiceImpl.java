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

@Service
public class WebClientServiceImpl implements WebClientService {

    private WebClient webClient;
    private ItemRepository itemRepository;


    @Autowired
    public WebClientServiceImpl(ItemRepository itemRepository) {
        this.webClient =  WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/search/shop.json?query=RTX 3060ti&display=100")
                .defaultHeader("X-Naver-Client-Id", "LN0TxgVzwBlIVpnW0QGb")
                .defaultHeader("X-Naver-Client-Secret", "_9ZdxXu6KD")
                .build();
        this.itemRepository = itemRepository;
    }



    public Mono<Root> getProducts() {
        Mono<Root> products = webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class);
      //  addProducts(products);
        return products;
    }

    @Override
    public void addProducts(Mono<Root> products) {
        Root addedProducts = products.block();
        List<Item> addedItems = addedProducts.getItems();
        itemRepository.saveAll(addedItems);
    }
}
