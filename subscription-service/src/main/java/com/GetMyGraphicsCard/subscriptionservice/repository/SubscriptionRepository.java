package com.GetMyGraphicsCard.subscriptionservice.repository;

import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
