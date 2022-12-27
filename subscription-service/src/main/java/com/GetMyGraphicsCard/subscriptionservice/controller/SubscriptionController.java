package com.GetMyGraphicsCard.subscriptionservice.controller;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import com.GetMyGraphicsCard.subscriptionservice.service.SubscriptionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionServiceImpl subscriptionService;

    @PostMapping()
    public ResponseEntity<String> makeSubscription() {
        return ResponseEntity.ok(subscriptionService.makeSubscription());
    }

    @GetMapping("{id}")
    public ResponseEntity<List<SubscriptionItemDto>> getAllSubscribedItems(@PathVariable("id") String subscriptionId) throws Exception {
       List<SubscriptionItemDto> subscriptionItemDtoList = subscriptionService.getAllSubscribedItems(Long.parseLong(subscriptionId));

       return ResponseEntity.ok(subscriptionItemDtoList);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> addItemToSubscription(@PathVariable("id") String subscriptionId, @RequestBody String productId) throws Exception {
        return ResponseEntity.ok(subscriptionService.addItemToSubscription(Long.parseLong(subscriptionId), productId));
    }

}
