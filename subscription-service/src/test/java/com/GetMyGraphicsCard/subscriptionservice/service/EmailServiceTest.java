package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.entity.Alert;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.repository.AlertRepository;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionItemRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
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


        for (Alert a: alerts) {
            System.out.println("Alert= " + a.toString());
        }

        List<SubscriptionItem> items = itemRepository.itemsToBeNotified();

        for (SubscriptionItem i : items) {
            System.out.println("Item = " + i.getTitle());
        }
    }


}
