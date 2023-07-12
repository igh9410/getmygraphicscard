package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;
import com.getmygraphicscard.subscriptionservice.entity.SubscriptionItem;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionItemRepository subscriptionItemRepository;

    private final RestTemplate restTemplate;

    private SubscriptionItemDto mapToDto(SubscriptionItem subscriptionItem) {
        return SubscriptionItemDto.builder()
                .id(subscriptionItem.getId())
                .title(subscriptionItem.getTitle())
                .link(subscriptionItem.getLink())
                .image(subscriptionItem.getImage())
                .lprice(subscriptionItem.getLprice())
                .build();
    }


    @Override
    public List<SubscriptionItemDto> getAllSubscribedItems(String userEmail) throws Exception {
        List<SubscriptionItem> subscriptionItemList = subscriptionItemRepository.findByUserEmail(userEmail);

        log.info("Retrieving all subscribed items");

        return subscriptionItemList.stream().map(this::mapToDto).toList();
    }
/*
    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "buildFallbackAddItemToSubscription")
    public SubscriptionItemDto addItemToSubscription(Subscription subscription, String id) throws Exception {

        log.info("Requesting product with id {} info to product-service", id);

        String url = "http://product-service/api/items/" + id;

        ResponseEntity<SubscriptionItem> responseEntity = restTemplate.getForEntity(url, SubscriptionItem.class);
        SubscriptionItem item = responseEntity.getBody();

        if (item == null || responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Failed to retrieve item");
        }
        subscription.addItem(item);

        // save to database
        subscriptionRepository.save(subscription);
        return mapToDto(item);
    }

    @SuppressWarnings("unused")
    public SubscriptionItemDto buildFallbackAddItemToSubscription(Long subscriptionId, String id, Exception e) {

        return SubscriptionItemDto.builder()
                .title("Sorry the product service is not available")
                .lprice(0)
                .link("")
                .image("")
                .build();
    }


    @Override
    public String removeItemFromSubscription(Subscription subscription, int index) throws Exception {

        SubscriptionItem removedItem = subscription.getSubscriptionItemList().get(index);
        subscription.removeItem(removedItem);

        return "Item deleted successfully.";
    }*/



}
