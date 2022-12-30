package com.GetMyGraphicsCard.subscriptionservice.controller;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
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


    @PostMapping("/")
    public ResponseEntity<String> makeSubscription() {
        return ResponseEntity.ok(subscriptionService.makeSubscription());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> removeSubscription(@PathVariable("id") String subscriptionId) throws Exception {
        if (subscriptionService.findById(Long.parseLong(subscriptionId)) == null) {
            throw new Exception("Subscription does not exist.");
        }
        return ResponseEntity.ok(subscriptionService.removeSubscription(Long.parseLong(subscriptionId)));
    }

    @GetMapping("{id}")
    public ResponseEntity<List<SubscriptionItemDto>> getAllSubscribedItems(@PathVariable("id") String subscriptionId) throws Exception {
       List<SubscriptionItemDto> subscriptionItemDtoList = subscriptionService.getAllSubscribedItems(Long.parseLong(subscriptionId));

       return ResponseEntity.ok(subscriptionItemDtoList);
    }

    @PostMapping("{id}")
    public ResponseEntity<String> addItemToSubscription(@PathVariable("id") String subscriptionId, @RequestBody String productId) throws Exception {
        return ResponseEntity.ok(subscriptionService.addItemToSubscription(Long.parseLong(subscriptionId), productId));
    }

    @DeleteMapping("{id}/{index}")
    public ResponseEntity<String> removeItemFromSubscription(@PathVariable("id") String subscriptionId, @PathVariable("index") int index) throws Exception {
        if (index >= subscriptionService.getAllSubscribedItems(Long.parseLong(subscriptionId)).size()) {
            throw new Exception("OutOfBound Exception");
        }
        return ResponseEntity.ok(subscriptionService.removeItemFromSubscription(Long.parseLong(subscriptionId), index));
    }

}
