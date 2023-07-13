package com.getmygraphicscard.identityservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "The username must not be blank or white spaces")
    private String username;

    @NotBlank(message = "The password must not be blank or white spaces")
    private String password;
}
