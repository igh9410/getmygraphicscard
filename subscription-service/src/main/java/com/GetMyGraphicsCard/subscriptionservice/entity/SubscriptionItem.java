package com.GetMyGraphicsCard.subscriptionservice.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "item")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String title;
    private String link;
    private String image;
    private int lprice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subscription_id")
    private Subscription subscription;


}
