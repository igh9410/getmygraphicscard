package com.GetMyGraphicsCard.subscriptionservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {

    private String username;
    private String password;
    private String email;
}
