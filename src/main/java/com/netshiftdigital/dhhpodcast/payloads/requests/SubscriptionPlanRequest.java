package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubscriptionPlanRequest {
    private Long id;
    @NotBlank(message = "Name must not be blank")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    private String name;
}
