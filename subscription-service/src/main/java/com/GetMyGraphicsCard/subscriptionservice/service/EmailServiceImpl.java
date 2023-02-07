package com.GetMyGraphicsCard.subscriptionservice.service;

import com.GetMyGraphicsCard.subscriptionservice.entity.Alert;
import com.GetMyGraphicsCard.subscriptionservice.entity.SubscriptionItem;
import com.GetMyGraphicsCard.subscriptionservice.repository.AlertRepository;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionItemRepository;
import com.GetMyGraphicsCard.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    private final SubscriptionItemRepository itemRepository;

    private final AlertRepository alertRepository;

    private static Logger logger =  LoggerFactory.getLogger(EmailServiceImpl.class);


    @Override
    @Scheduled(cron = "0 0/5 * * * ?")
    public void sendMailToSubscribers() {
        List<SubscriptionItem> items = itemRepository.itemsToBeNotified();

        // send emails to subscribers every 5 minute
        for (SubscriptionItem i : items) {
            SimpleMailMessage message = new SimpleMailMessage();
            String receiverEmail = i.getSubscription().getEmail();
            message.setFrom("athanasia9410@gmail.com");
            message.setTo(receiverEmail);
            message.setSubject("Your subscribed item " +  i.getTitle() + " hits the lowest price!");
            message.setText("The lowest price for product name " + i.getTitle() + " , link: " + i.getLink() + " is now available");
            emailSender.send(message);

        }
        // Delete the saved alerts after sending emails;
        alertRepository.deleteAll();
    }
}
