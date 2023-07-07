package com.getmygraphicscard.subscriptionservice.repository;

import com.getmygraphicscard.subscriptionservice.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


   Optional<Subscription> findByEmail(String email);
   boolean existsByEmail(String email);
}