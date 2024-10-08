package com.netshiftdigital.dhhpodcast.payloads.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = " Last Name is required")
    private String lastName;
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;
}
