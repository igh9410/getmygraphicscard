package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.entity.Alert;
import com.getmygraphicscard.subscriptionservice.event.PriceAlert;
import com.getmygraphicscard.subscriptionservice.repository.AlertRepository;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final AlertRepository alertRepository;

    private final SubscriptionItemRepository itemRepository;


    @KafkaListener(topics = "alertTopic", groupId = "groupId", containerFactory = "alertListener")
    public void consume(PriceAlert data) {
        log.info("Listener received: " + data.toString());
        Alert alert = new Alert();
        alert.setTitle(data.getTitle());
        alert.setLink(data.getLink());
        alert.setProductId(data.getProductId());
        alert.setLprice(data.getLprice());

        alertRepository.save(alert);
    }
}
