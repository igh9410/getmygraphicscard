package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;

import java.util.List;

public interface SubscriptionService {

    List<SubscriptionItemDto> getAllSubscribedItems(String userEmail) throws Exception;

//    SubscriptionItemDto addItemToSubscription(Subscription subscription, String id) throws Exception;

 //   String removeItemFromSubscription(Subscription subscription, int index) throws Exception;

}





