package com.getmygraphicscard.subscriptionservice.dto;


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

    @Email(message = "The email must be valid.")
    private String email;

    @NotBlank
    @Size(min=6, max=20, message = "The password must be between 6 and 20 characters.")
    private String password;



}
