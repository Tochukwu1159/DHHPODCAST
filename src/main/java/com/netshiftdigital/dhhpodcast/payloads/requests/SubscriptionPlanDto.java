package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SubscriptionPlanDto {

//    @NotBlank(message = "Plan code is mandatory")
//    private String planCode;

    @Min(value = 1, message = "Plan ID must be a positive integer")
    private Long planId;

    @NotNull(message = "Amount is mandatory")
    @Min(value = 100, message = "Amount must be at least 100") // Adjust minimum as needed
    private Double amount;

    @NotBlank(message = "Interval is mandatory ( monthly)")
    private String interval;

    // Getters and Setters (provided for completeness)
//    public String getPlanCode() {
//        return planCode;
//    }

//    public void setPlanCode(String planCode) {
//        this.planCode = planCode;
//    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }
}


