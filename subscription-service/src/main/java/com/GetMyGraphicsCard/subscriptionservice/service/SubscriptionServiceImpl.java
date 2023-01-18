package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.config.JwtService;
import com.GetMyGraphicsCard.subscriptionservice.dto.AuthenticationResponse;
import com.GetMyGraphicsCard.subscriptionservice.dto.LoginRequest;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionDto;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.enums.Role;
import com.GetMyGraphicsCard.subscriptionservice.exception.NoSubscriptionException;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final WebClient webClient;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public SubscriptionDto makeSubscription(SubscriptionDto subscriptionDto) {
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
    //    Subscription result = subscriptionRepository.getReferenceById(subscriptionId);
        subscriptionRepository.deleteById(subscriptionId);
        return "Subscription deleted";
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = subscriptionRepository.findByEmail(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public Subscription findSubscriptionByEmail(String email) {
        return subscriptionRepository.findByEmail(email).orElseThrow(() -> new NoSubscriptionException("Not a registered user"));
    }

    @Override
    public Subscription findSubscriptionById(Long subscriptionId) {
        return subscriptionRepository.getReferenceById(subscriptionId);
    }


    @Override
    @PreAuthorize("#ssub")
    public List<SubscriptionItemDto> getAllSubscribedItems(Long subscriptionId) throws Exception {
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);

        if (result.isEmpty()) {
           throw new Exception("Subscription does not exists.");
        }

        log.info("Retrieving all subscribed items");

        return result.get().getSubscriptionItemList().stream().map(this::mapToDto).toList();
    }

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "buildFallbackAddItemToSubscription")
    public SubscriptionItemDto addItemToSubscription(Long subscriptionId, String id) throws Exception {
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);

        if (result.isEmpty()) {
            throw new Exception("Please make a subscription to app first");
        }

        log.info("Requesting product with id {} info to product-service", id);
        SubscriptionItem item = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/items/{id}")
                        .build(id))
                .retrieve()
                .bodyToMono(SubscriptionItem.class)
                .timeout(Duration.ofSeconds(3))
                .block();
        assert item != null;

        result.get().addItem(item);

        // save to database
        subscriptionRepository.save(result.get());
        return mapToDto(item);
    }

    public SubscriptionItemDto buildFallbackAddItemToSubscription(Long subscriptionId, String id, Exception e) {
        SubscriptionItemDto subscriptionItemDto = SubscriptionItemDto.builder()
                .title("Sorry the product service is not available")
                .lprice(0)
                .link("")
                .image("")
                .build();
        return subscriptionItemDto;
    }



    @Override
    public String removeItemFromSubscription(Long subscriptionId,  int index) throws Exception {
        Optional<Subscription> result = subscriptionRepository.findById(subscriptionId);
        if (result.isEmpty()) {
            throw new Exception("Subscription does not exists.");
        }
        SubscriptionItem removedItem = result.get().getSubscriptionItemList().get(index);
        result.get().removeItem(removedItem);

        String responseMessage = "Item deleted successfully.";
        return responseMessage;
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
