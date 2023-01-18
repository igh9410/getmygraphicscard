package com.GetMyGraphicsCard.subscriptionservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {


    @NotBlank
    @Size(min=4, max=12, message = "The username must be between 4 and 12 characters.")
    private String username;
    @NotBlank
    @Size(min=6, max=20, message = "The password must be between 6 and 20 characters.")
    private String password;

    @Email
    private String email;

}
