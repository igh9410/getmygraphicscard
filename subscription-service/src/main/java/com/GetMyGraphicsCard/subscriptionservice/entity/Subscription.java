package com.GetMyGraphicsCard.subscriptionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany(mappedBy = "subscription",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionItem> subscriptionItemList = new ArrayList<>();

    public void addItem(SubscriptionItem item) {
        subscriptionItemList.add(item);
        item.setSubscription(this);
    }

    public void removeItem(SubscriptionItem item) {
        subscriptionItemList.remove(item);
        item.setSubscription(null);
    }
}
