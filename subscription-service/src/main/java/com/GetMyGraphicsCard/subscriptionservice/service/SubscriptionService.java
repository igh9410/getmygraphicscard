package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;

import java.util.List;

public interface SubscriptionService {

    String makeSubscription();
    List<SubscriptionItemDto> getAllSubscribedItems(Long subscriptionId) throws Exception;

    String addItemToSubscription(Long subscriptionId, String id) throws Exception;

  //  String deleteItemFromSubscription(Long subscriptionId, String id) throws Exception;

}
