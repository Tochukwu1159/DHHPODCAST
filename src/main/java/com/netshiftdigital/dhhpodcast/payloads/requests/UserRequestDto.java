package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {

    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email
            (message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Transient // Excluded from serialization/deserialization (optional)
    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

    @Pattern(regexp = "^[\\d+\\-() ]*$", message = "Invalid phone number format")
    private String phoneNumber;
}