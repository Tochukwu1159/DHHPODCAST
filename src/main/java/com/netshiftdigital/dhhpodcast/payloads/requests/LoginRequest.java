package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    private @NotBlank(message = "Email address is mandatory") @Email(message = "Invalid email format") String email;

    private @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password must be at least 8 characters long") String password;

}
