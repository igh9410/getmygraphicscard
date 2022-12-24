package com.GetMyGraphicsCard.subscriptionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionItemDto {

    private Long Id;
    private String title;
    private String link;
    private String image;
    private int lprice;
}
