package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionDto;
import com.GetMyGraphicsCard.subscriptionservice.dto.SubscriptionItemDto;
import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.enums.Role;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Import({SubscriptionServiceImpl.class})
public class SubscriptionServiceTest {

    @Mock
    SubscriptionRepository subscriptionRepository;

    @InjectMocks
    SubscriptionServiceImpl subscriptionService;

    @Mock
    PasswordEncoder passwordEncoder;
    Subscription subscription;

    @BeforeEach
    public void setUpSubscription() {
        subscription = Subscription.builder()
                .id(1L)
                .password("test")
                .email("test@gmail.com")
                .role(Role.USER)
                .subscriptionItemList(new ArrayList<>())
                .build();

        subscription.getSubscriptionItemList().add(SubscriptionItem.builder().subscription(subscription)
                .title("RTX 3050")
                .image("test1")
                .link("test1")
                .lprice(20000).build());
        subscription.getSubscriptionItemList().add(SubscriptionItem.builder().subscription(subscription)
                .title("RTX 3060")
                .image("test2")
                .link("test2")
                .lprice(30000).build());
        subscription.getSubscriptionItemList().add(SubscriptionItem.builder().subscription(subscription)
                .title("RTX 3070")
                .image("test3")
                .link("test3")
                .lprice(40000).build());
    }


    @Test
    void makeSubscriptionTest() {
        // given
        when(subscriptionRepository.save(any(Subscription.class))).then(returnsFirstArg());

        //when
        SubscriptionDto subscriptionDto = SubscriptionDto.builder()
                .password(subscription.getPassword())
                .email(subscription.getEmail())
                .build();

        SubscriptionDto expected = subscriptionService.makeSubscription(subscriptionDto);

        assertEquals("test", expected.getPassword());
        assertEquals("test@gmail.com", expected.getEmail());

        verify(subscriptionRepository).save(any());

    }

    @Test
    void removeSubscriptionTest() {
        Long subscriptionId = 1L;

        willDoNothing().given(subscriptionRepository).deleteById(subscriptionId);

        subscriptionService.removeSubscription(subscriptionId);

        verify(subscriptionRepository, times(1)).deleteById(subscriptionId);
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

        subscription.addItem(subscriptionItem);
        assertEquals(subscription.getSubscriptionItemList().get(3).getTitle(), subscriptionItem.getTitle(), "This should be equal");

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
        subscription.addItem(subscriptionItem);
        subscription.removeItem(subscriptionItem);
        assertEquals(3, subscription.getSubscriptionItemList().size(), "Should be 3");
    }

    @Test
    void getAllSubscribedItemsTest() throws Exception {
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));
        List<SubscriptionItemDto> testItems = subscriptionService.getAllSubscribedItems(1L);
        assertEquals("RTX 3050", testItems.get(0).getTitle());
        assertEquals("RTX 3060", testItems.get(1).getTitle());
        assertEquals("RTX 3070", testItems.get(2).getTitle());
    }

    @Test
    void getNotifiedUsersByLinkTest() {
        Subscription subscription2 = Subscription.builder()
                .id(2L)
                .password("test2")
                .email("test2@gmail.com")
                .role(Role.USER)
                .subscriptionItemList(new ArrayList<>())
                .build();
        subscription2.getSubscriptionItemList().add(SubscriptionItem.builder().subscription(subscription)
                .title("RTX 3050")
                .image("test1")
                .link("test1")
                .lprice(20000).build());
        Subscription subscription3 = Subscription.builder()
                .id(3L)
                .password("test3")
                .email("test3@gmail.com")
                .role(Role.USER)
                .subscriptionItemList(new ArrayList<>())
                .build();
        subscription2.getSubscriptionItemList().add(SubscriptionItem.builder().subscription(subscription)
                .title("RTX 3060")
                .image("test2")
                .link("test2")
                .lprice(300000).build());

        String link = "test1";
        List<Subscription> testUsers = new ArrayList<>();
        testUsers.add(subscription);
        testUsers.add(subscription2);
        testUsers.add(subscription3);
        
        System.out.println(testUsers.isEmpty());




    }
}


