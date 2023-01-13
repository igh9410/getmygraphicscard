package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class SubscriptionServiceTest {

    @MockBean
    SubscriptionRepository subscriptionRepository;

    @Autowired
    SubscriptionService subscriptionService;

    Subscription subscription;

    @BeforeEach
    public void setUpSubscription() {
        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setSubscriptionItemList(new ArrayList<>());
    }

    @Test
    public void addItemToSubscriptionTest() throws Exception {
        SubscriptionItem subscriptionItem = SubscriptionItem.builder()
                .id(1L)
                .image("image")
                .link("link")
                .title("RTX 3060TI")
                .lprice(200000)
                .build();
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        subscription.addItem(subscriptionItem);
        assertEquals(subscription.getSubscriptionItemList().get(0).getTitle(), subscriptionItem.getTitle(), "This should be equal");

    }

    @Test
    public void removeItemFromSubscriptionTest() {
        SubscriptionItem subscriptionItem = SubscriptionItem.builder()
                .id(1L)
                .image("image")
                .link("link")
                .title("RTX 3060TI")
                .lprice(200000)
                .build();
        subscription.addItem(subscriptionItem);
        subscription.removeItem(subscriptionItem);
        assertEquals(0, subscription.getSubscriptionItemList().size(), "Should be 0");
    }
}


