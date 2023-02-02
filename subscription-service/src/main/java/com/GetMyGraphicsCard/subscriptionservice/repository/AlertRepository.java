package com.GetMyGraphicsCard.subscriptionservice.repository;

import com.GetMyGraphicsCard.subscriptionservice.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    @Query(value = "SELECT a FROM Alert a INNER JOIN SubscriptionItem i ON a.link=i.link")
    Collection<Alert> findAlertsToBeSent();
}
