package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubscriptionPlansRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Amount is mandatory")
    @Min(value =100, message = "Amount must be at least 100") // Adjust minimum as needed
    private Double amount;

    @NotBlank(message = "Intervals are mandatory (e.g. monthly)")
    private String intervals;

    @NotBlank(message = "Currency is mandatory (e.g., NGN, USD)")
    private String currency;

    @NotBlank(message = "Paystack plan code is mandatory")
    private String paystackPlanCode;

    @Min(value = 1, message = "Plan ID must be a positive integer (if provided)")
    private Long planId;
}

