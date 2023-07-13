package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionItemDto> getAllSubscribedItems(String userEmail) throws Exception;

    SubscriptionItemDto addItemToSubscription(String userEmail, String productId) throws Exception;

    String removeItemFromSubscription(String userEmail, int index) throws Exception;

}





