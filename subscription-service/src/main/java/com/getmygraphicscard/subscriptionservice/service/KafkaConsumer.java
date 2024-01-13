package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.entity.Alert;
import com.getmygraphicscard.subscriptionservice.event.PriceAlert;
import com.getmygraphicscard.subscriptionservice.repository.AlertRepository;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final AlertRepository alertRepository;

    private final SubscriptionItemRepository itemRepository;

    private final RedisLockRegistry redisLockRegistry;


    @KafkaListener(topics = "alertTopic", groupId = "groupId", containerFactory = "alertListener")
    public void consume(PriceAlert data) {
        Lock lock = redisLockRegistry.obtain("sendMailToSubscribers");
        if (lock.tryLock()) { // Using Distributed Lock to prevent multiple execution
            try {
                log.info("Listener received: " + data.toString());
                Alert alert = new Alert();
                alert.setTitle(data.getTitle());
                alert.setLink(data.getLink());
                alert.setProductId(data.getProductId());
                alert.setLprice(data.getLprice());

                alertRepository.save(alert);
            } finally {
                lock.unlock();
            }
        }
    }
}
