package com.GetMyGraphicsCard.productservice.service;


import com.GetMyGraphicsCard.productservice.entity.Root;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientServiceImpl implements WebClientService {

    private WebClient webClient;
    private Gson gson;


    @Autowired
    public WebClientServiceImpl() {
        this.webClient =  WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/search/shop.json?query=RTX 3060ti&display=100")
                .defaultHeader("X-Naver-Client-Id", "LN0TxgVzwBlIVpnW0QGb")
                .defaultHeader("X-Naver-Client-Secret", "_9ZdxXu6KD")
                .build();
        this.gson = new Gson();
    }



    public Mono<Root> getProducts() {
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Root.class);
    }
}
