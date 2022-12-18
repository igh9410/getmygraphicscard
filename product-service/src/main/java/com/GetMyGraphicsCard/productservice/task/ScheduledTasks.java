package com.GetMyGraphicsCard.productservice.task;

import com.GetMyGraphicsCard.productservice.entity.Root;
import com.GetMyGraphicsCard.productservice.service.WebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ScheduledTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static String[] chipsets = {"RTX 3050", "RTX 3060", "RTX 3060ti"};
    @Autowired
    private WebClientService webClientService;

    @Scheduled(cron = "0 0/5 * * * ?") // Sending Http requests to Naver API every 5 minutes and save to DB
    public void GetGraphicsCardDataFromNaver() {
        Mono<Root> request1 = webClientService.requestGraphicsCardInfo("RTX 3050");
        Mono<Root> request2 = webClientService.requestGraphicsCardInfo("RTX 4080");
        Mono<Root> request3 = webClientService.requestGraphicsCardInfo("RTX 4090");
        Mono<Root> mergedRequests = Flux.merge(request1, request2, request3).next();
        log.info("Sending Http requests..");
        webClientService.addGraphicsCardToDB(mergedRequests);
    }
}
