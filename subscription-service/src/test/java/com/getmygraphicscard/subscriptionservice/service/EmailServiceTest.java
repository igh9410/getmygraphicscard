package com.getmygraphicscard.subscriptionservice.service;


import com.getmygraphicscard.subscriptionservice.entity.Alert;
import com.getmygraphicscard.subscriptionservice.entity.SubscriptionItem;
import com.getmygraphicscard.subscriptionservice.repository.AlertRepository;
import com.getmygraphicscard.subscriptionservice.repository.SubscriptionItemRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    SubscriptionItemRepository itemRepository;

    @Autowired
    AlertRepository alertRepository;


    @Test
    @Transactional
    public void sendEmailTest() {

        List<Alert> alerts = alertRepository.findAlertsToBeSent().stream().toList();


        for (Alert a : alerts) {
            System.out.println("Alert= " + a.toString());
        }

        List<SubscriptionItem> items = itemRepository.itemsToBeNotified();

        for (SubscriptionItem i : items) {
            System.out.println("Item = " + i.getTitle());
        }
    }


}
