package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionItemDto> getAllSubscribedItems(Long subscriptionId) throws Exception;

    void addItemToSubscription(Long subscriptionId, String id);

    void deleteItemFromSubscription(Subscription subscription, int index) throws Exception;

}
