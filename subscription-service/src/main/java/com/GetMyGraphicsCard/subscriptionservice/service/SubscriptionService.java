package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionDto;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDto makeSubscription(SubscriptionDto subscriptionDto);

    String removeSubscription(Long subscriptionId);

    Subscription findByEmail(String email);
    Subscription findById(Long subscriptionId);

    List<SubscriptionItemDto> getAllSubscribedItems(Long subscriptionId) throws Exception;

    SubscriptionItemDto addItemToSubscription(Long subscriptionId, String id) throws Exception;

    String removeItemFromSubscription(Long subscriptionId, int id) throws Exception;




}
