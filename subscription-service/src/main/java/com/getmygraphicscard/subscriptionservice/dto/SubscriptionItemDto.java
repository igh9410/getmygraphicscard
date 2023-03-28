package com.getmygraphicscard.subscriptionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionItemDto {
    private String title;
    private String link;
    private String image;
    private int lprice;
}
