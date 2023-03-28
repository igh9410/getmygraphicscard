package com.getmygraphicscard.subscriptionservice.entity;

import com.getmygraphicscard.subscriptionservice.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionItem> subscriptionItemList;

    public void addItem(SubscriptionItem item) {
        if (subscriptionItemList == null) {
            subscriptionItemList = new ArrayList<>();
        }
        subscriptionItemList.add(item);
        item.setSubscription(this);
    }

    public void removeItem(SubscriptionItem item) {
        subscriptionItemList.remove(item);
        item.setSubscription(null);
    }


    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", subscriptionItemList=" + subscriptionItemList +
                '}';
    }


}
