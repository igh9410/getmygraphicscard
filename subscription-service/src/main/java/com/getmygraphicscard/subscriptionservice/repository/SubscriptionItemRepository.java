package com.getmygraphicscard.subscriptionservice.repository;

import com.getmygraphicscard.subscriptionservice.entity.SubscriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SubscriptionItemRepository extends JpaRepository<SubscriptionItem, Long> {

    @Query(value = "SELECT i FROM SubscriptionItem i INNER JOIN Alert a ON i.link=a.link")
    List<SubscriptionItem> itemsToBeNotified();

    List<SubscriptionItem> findByUserEmail(String userEmail);

}
