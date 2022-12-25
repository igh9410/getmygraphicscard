package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.dto.ItemResponse;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final WebClient webClient;


    @Override
    public List<SubscriptionItemDto> getAllSubscribedItems(Long subscriptionId) throws Exception {
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);
        Subscription mockSubscription = null;
        if (result.isPresent()) {
            mockSubscription = result.get();
        } else {
            throw new Exception("Subscription does not exists.");
        }
        log.info("Retrieving all subscribed items");

        return mockSubscription.getSubscriptionItemList().stream().map(this::mapToDto).toList();
    }

    @Override
    public void addItemToSubscription(Long subscriptionId, String id) {
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);
        Subscription mockSubscription = null;
        if (result.isPresent()) {
            mockSubscription = result.get();
        } else {
            mockSubscription = new Subscription();
            mockSubscription.setSubscriptionItemList(new ArrayList<SubscriptionItem>());
        }

        log.info("Requesting product with id {} info to product-service", id);
        ItemResponse graphicsCard = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/items/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(ItemResponse.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        SubscriptionItem requestedItem = new SubscriptionItem();

        requestedItem.setTitle(graphicsCard.getTitle());
        requestedItem.setLink(graphicsCard.getLink());
        requestedItem.setImage(graphicsCard.getImage());
        requestedItem.setLprice(graphicsCard.getLprice());

        List<SubscriptionItem> modifiedList = mockSubscription.getSubscriptionItemList();
        modifiedList.add(requestedItem);

        mockSubscription.setSubscriptionItemList(modifiedList);
        // save to database
        subscriptionRepository.save(mockSubscription);
        
    }

    @Override
    public void deleteItemFromSubscription(Subscription subscription, int index) throws Exception {
        Optional<Subscription> result = subscriptionRepository.findById(subscription.getId());
        Subscription mockSubscription = null;
        if (result.isPresent()) {
            mockSubscription = result.get();
        } else {
            throw new Exception("Subscription does not exists.");
        }

     //   List<SubscriptionItem> subscriptionItem = mockSubscription.getSubscriptionItemList().get(index).orElseT;

    }

    private SubscriptionItemDto mapToDto(SubscriptionItem subscriptionItem) {
        return SubscriptionItemDto.builder()
                .title(subscriptionItem.getTitle())
                .link(subscriptionItem.getLink())
                .image(subscriptionItem.getImage())
                .lprice(subscriptionItem.getLprice())
                .build();
    }
}
