package com.GetMyGraphicsCard.subscriptionservice.controller;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import com.GetMyGraphicsCard.subscriptionservice.exception.NoSubscriptionException;
import com.GetMyGraphicsCard.subscriptionservice.model.SecurityUser;
import com.GetMyGraphicsCard.subscriptionservice.service.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
@CrossOrigin("http://localhost:3000")
public class SubscriptionController {
    private final SubscriptionServiceImpl subscriptionService;

    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeSubscription(@PathVariable("id") Long subscriptionId) throws Exception {
        Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
        if (subscription == null) {
            throw new NoSubscriptionException("Subscription does not exist.");
        }
        return ResponseEntity.ok(subscriptionService.removeSubscription(subscriptionId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<List<SubscriptionItemDto>> getAllSubscribedItems(@PathVariable("id") Long subscriptionId) throws Exception {
       Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
       if (subscription == null) {
           throw new NoSubscriptionException("Subscription does not exist.");
       }
       List<SubscriptionItemDto> subscriptionItemDtoList = subscriptionService.getAllSubscribedItems(subscription);
       return ResponseEntity.ok(subscriptionItemDtoList);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SubscriptionItemDto> addItemToSubscription(@PathVariable("id") Long subscriptionId, @RequestBody String productId) throws Exception {
       Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
       if (subscription == null) {
           throw new NoSubscriptionException("Subscription does not exist.");
       }
       return ResponseEntity.ok(subscriptionService.addItemToSubscription(subscription, productId));
    }

    @DeleteMapping("{id}/{index}")
    public ResponseEntity<String> removeItemFromSubscription(@PathVariable("id") Long subscriptionId, @PathVariable("index") int index) throws Exception {
        Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId);
        if (subscription == null) {
            throw new NoSubscriptionException("Subscription does not exist.");
        }
        if (index >= subscriptionService.getAllSubscribedItems(subscription).size()) {
            throw new IndexOutOfBoundsException("OutOfBoundsException");
        }
        return ResponseEntity.ok(subscriptionService.removeItemFromSubscription(subscription, index));
    }



}
