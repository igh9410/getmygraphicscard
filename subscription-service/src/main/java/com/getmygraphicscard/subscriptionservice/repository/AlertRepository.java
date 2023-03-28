package com.getmygraphicscard.subscriptionservice.repository;

import com.getmygraphicscard.subscriptionservice.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    @Query(value = "SELECT a FROM Alert a INNER JOIN SubscriptionItem i ON a.link=i.link")
    Collection<Alert> findAlertsToBeSent();

    Optional<Alert> findByLink(String link);
}
