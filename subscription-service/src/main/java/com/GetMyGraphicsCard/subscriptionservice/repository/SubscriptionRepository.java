package com.GetMyGraphicsCard.subscriptionservice.repository;

import com.GetMyGraphicsCard.subscriptionservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

   Optional<Subscription> findByEmail(String email);

}
