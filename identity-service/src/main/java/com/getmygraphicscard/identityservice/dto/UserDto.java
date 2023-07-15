package com.getmygraphicscard.identityservice.dto;


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
public class UserDto {

    @NotBlank
    @Size(min=6, max=12, message = "The username must be between 6 and 12 characters.")
    private String username;

    @Email(message = "The email must be valid.")
    private String email;

    @NotBlank
    @Size(min=6, max=20, message = "The password must be between 6 and 20 characters.")
    private String password;



}
