package com.GetMyGraphicsCard.subscriptionservice.controller;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.service.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;

    @GetMapping("{id}")
    public List<SubscriptionItemDto> getAllSubscribedItems(@PathVariable("id") String subscriptionId) throws Exception {
        return subscriptionService.getAllSubscribedItems(Long.parseLong(subscriptionId));
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addItemToSubscription(@PathVariable("id") String subscriptionId, @RequestBody String productId) {
        subscriptionService.addItemToSubscription(Long.parseLong(subscriptionId), productId);
    }

}
