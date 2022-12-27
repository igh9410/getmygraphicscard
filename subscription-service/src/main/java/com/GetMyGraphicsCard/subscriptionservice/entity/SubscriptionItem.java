package com.GetMyGraphicsCard.subscriptionservice.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
@Builder
public class SubscriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private String link;
    private String image;
    private int lprice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;

    @Override
    public  boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionItem)) {
            return false;
        }
        return Id != null && Id.equals(((SubscriptionItem) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
