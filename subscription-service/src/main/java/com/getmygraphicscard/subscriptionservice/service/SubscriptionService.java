package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.dto.SubscriptionDto;
import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;
import com.getmygraphicscard.subscriptionservice.entity.Subscription;

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





