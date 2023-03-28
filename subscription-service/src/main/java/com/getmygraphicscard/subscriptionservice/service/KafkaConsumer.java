package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.entity.Alert;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.event.PriceAlert;
import com.GetMyGraphicsCard.subscriptionservice.repository.AlertRepository;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final AlertRepository alertRepository;

    private final SubscriptionItemRepository itemRepository;


    @KafkaListener(topics = "alertTopic", groupId = "groupId", containerFactory = "alertListener")
    public void consume(PriceAlert data) {
        System.out.println("Listener received: " + data.toString());
        Alert alert = new Alert();
        alert.setTitle(data.getTitle());
        alert.setLink(data.getLink());
        alert.setProductId(data.getProductId());
        alert.setLprice(data.getLprice());

        alertRepository.save(alert);
    }
}
