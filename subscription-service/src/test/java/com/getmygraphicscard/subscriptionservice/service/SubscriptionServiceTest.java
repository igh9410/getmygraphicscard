package com.getmygraphicscard.subscriptionservice.service;


import com.getmygraphicscard.subscriptionservice.dto.SubscriptionItemDto;
import com.getmygraphicscard.subscriptionservice.entity.SubscriptionItem;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@Import({SubscriptionServiceImpl.class})
public class SubscriptionServiceTest {

    @Mock
    SubscriptionItemRepository subscriptionItemRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    SubscriptionServiceImpl subscriptionService;


    @BeforeEach
    public void setUpSubscriptionItems() {
        SubscriptionItem subscriptionItem1 = SubscriptionItem.builder()
                .title("RTX 3050")
                .image("test1")
                .link("test1")
                .userEmail("test1@gmail.com")
                .lprice(200000).build();

        SubscriptionItem subscriptionItem2 = SubscriptionItem.builder()
                .title("RTX 3060")
                .image("test2")
                .link("test2")
                .userEmail("test1@gmail.com")
                .lprice(300000).build();

        SubscriptionItem subscriptionItem3 = SubscriptionItem.builder()
                .title("RTX 3060Ti")
                .image("test3")
                .link("test3")
                .userEmail("test2@gmail.com")
                .lprice(400000).build();

        SubscriptionItem subscriptionItem4 = SubscriptionItem.builder()
                .title("RTX 3070")
                .image("test4")
                .link("test4")
                .userEmail("test2@gmail.com")
                .lprice(500000).build();

        subscriptionItemRepository.save(subscriptionItem1);
        subscriptionItemRepository.save(subscriptionItem2);
        subscriptionItemRepository.save(subscriptionItem3);
        subscriptionItemRepository.save(subscriptionItem4);
    }

    @Test
    void getAllSubscribedItemsTest() throws Exception {
        // Given
        String userEmail = "test@test.com";
        SubscriptionItem subscriptionItem = new SubscriptionItem();
        subscriptionItem.setTitle("Test Item");
        // Add any other necessary fields
        when(subscriptionItemRepository.findByUserEmail(userEmail)).thenReturn(List.of(subscriptionItem));

        // When
        List<SubscriptionItemDto> items = subscriptionService.getAllSubscribedItems(userEmail);

        // Then
        assertEquals(1, items.size());
        assertEquals("Test Item", items.get(0).getTitle());

    }

    @Test
    void addItemToSubscriptionTest() throws Exception {
        SubscriptionItem subscriptionItem = SubscriptionItem.builder()
                .id(1L)
                .image("image")
                .link("link")
                .title("RTX 3060TI")
                .lprice(200000)
                .build();

        subscriptionItemRepository.save(subscriptionItem);


    }

    @Test
    void removeItemFromSubscriptionTest() {
        SubscriptionItem subscriptionItem = SubscriptionItem.builder()
                .id(1L)
                .image("image")
                .link("link")
                .title("RTX 3060TI")
                .lprice(200000)
                .build();

    }


}


