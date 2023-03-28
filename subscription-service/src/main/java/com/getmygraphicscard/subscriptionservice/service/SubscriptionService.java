package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionDto;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.security.Principal;
import java.util.List;

public interface SubscriptionService {

    SubscriptionDto makeSubscription(SubscriptionDto subscriptionDto);

    String removeSubscription(Long subscriptionId);

    Subscription findSubscriptionByEmail(String email);

    Subscription findSubscriptionById(Long subscriptionId);

    List<SubscriptionItemDto> getAllSubscribedItems(Subscription subscription) throws Exception;

    SubscriptionItemDto addItemToSubscription(Subscription subscription, String id) throws Exception;

    String removeItemFromSubscription(Subscription subscription, int index) throws Exception;
}





