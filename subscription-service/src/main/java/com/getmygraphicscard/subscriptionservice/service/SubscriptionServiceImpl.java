package com.getmygraphicscard.subscriptionservice.service;

import com.getmygraphicscard.subscriptionservice.dto.SubscriptionDto;
import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;
import com.getmygraphicscard.subscriptionservice.entity.Subscription;
import com.getmygraphicscard.subscriptionservice.entity.SubscriptionItem;
import com.getmygraphicscard.subscriptionservice.enums.Role;
import com.getmygraphicscard.subscriptionservice.exception.DuplicateSubscriptionException;
import com.getmygraphicscard.subscriptionservice.exception.NoSubscriptionException;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final WebClient webClient;
    private final PasswordEncoder passwordEncoder;





    @Override
    public SubscriptionDto makeSubscription(SubscriptionDto subscriptionDto) {
        if (subscriptionRepository.existsByEmail(subscriptionDto.getEmail())) {
            throw new DuplicateSubscriptionException("Email already registered");
        }
        Subscription subscription = Subscription.builder()
                .email(subscriptionDto.getEmail())
                .password(passwordEncoder.encode(subscriptionDto.getPassword()))
                .role(Role.USER)
                .build();
        subscriptionRepository.save(subscription);
        log.info("New user registered");

        return subscriptionDto;
    }

    @Override
    public String removeSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
        return "Subscription deleted";
    }

    @Override
    public Subscription findSubscriptionByEmail(String email) {
        return subscriptionRepository.findByEmail(email).orElseThrow(() -> new NoSubscriptionException("Not a registered user"));
    }

    @Override
    public Subscription findSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.getReferenceById(subscriptionId);
    }



    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or #subscription.email == authentication.name")
    @Override
    public List<SubscriptionItemDto> getAllSubscribedItems(Subscription subscription) throws Exception {

        log.info("Retrieving all subscribed items");

        return subscription.getSubscriptionItemList().stream().map(this::mapToDto).toList();
    }

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "buildFallbackAddItemToSubscription")
    public SubscriptionItemDto addItemToSubscription(Subscription subscription, String id) throws Exception {

        log.info("Requesting product with id {} info to product-service", id);


        SubscriptionItem item = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/items/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(SubscriptionItem.class)
                .timeout(Duration.ofSeconds(3))
                .block();


        subscription.addItem(item);

        // save to database
        subscriptionRepository.save(subscription);
        return mapToDto(item);
    }

    @SuppressWarnings("unused")
    public SubscriptionItemDto buildFallbackAddItemToSubscription(Long subscriptionId, String id, Exception e) {

        return SubscriptionItemDto.builder()
                .title("Sorry the product service is not available")
                .lprice(0)
                .link("")
                .image("")
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or #subscription.email == authentication.name")
    @Override
    public String removeItemFromSubscription(Subscription subscription, int index) throws Exception {

        SubscriptionItem removedItem = subscription.getSubscriptionItemList().get(index);
        subscription.removeItem(removedItem);

        return "Item deleted successfully.";
    }





    private SubscriptionItemDto mapToDto(SubscriptionItem subscriptionItem) {
        return SubscriptionItemDto.builder()
                .title(subscriptionItem.getTitle())
                .link(subscriptionItem.getLink())
                .image(subscriptionItem.getImage())
                .lprice(subscriptionItem.getLprice())
                .build();
    }
}
