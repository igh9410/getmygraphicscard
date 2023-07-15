package com.getmygraphicscard.subscriptionservice.controller;

import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;
import com.getmygraphicscard.subscriptionservice.exception.NoSubscriptionException;
import com.getmygraphicscard.subscriptionservice.interceptor.JwtInterceptor;
import com.getmygraphicscard.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
@CrossOrigin("http://localhost:3000")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final JwtInterceptor jwtInterceptor;

    private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class);


    @GetMapping("/")
    public ResponseEntity<List<SubscriptionItemDto>> getAllSubscribedItems() throws Exception {
       String userEmail = jwtInterceptor.getUserEmail();
       log.info("Logged in user email = " + userEmail);
       List<SubscriptionItemDto> subscriptionItemDtoList = subscriptionService.getAllSubscribedItems(userEmail);

       return ResponseEntity.ok(subscriptionItemDtoList);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SubscriptionItemDto> addItemToSubscription(@RequestBody String productId) throws Exception {
       String userEmail = jwtInterceptor.getUserEmail();

       return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.addItemToSubscription(userEmail, productId));
    }


    @DeleteMapping("/{index}")
    public ResponseEntity<String> removeItemFromSubscription(@PathVariable("index") int index) throws Exception {
        String userEmail = jwtInterceptor.getUserEmail();
        if (index < 0 || index >= subscriptionService.getAllSubscribedItems(userEmail).size()) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        subscriptionService.removeItemFromSubscription(userEmail, index);

        return ResponseEntity.noContent().build();
    }



}
