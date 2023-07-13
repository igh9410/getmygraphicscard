package com.getmygraphicscard.subscriptionservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "subscription_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"link", "user_email"}))
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
    @Column(name="user_email")
    private String userEmail;
    @Column(name="created_time")
    private LocalDateTime createdTime;


}
