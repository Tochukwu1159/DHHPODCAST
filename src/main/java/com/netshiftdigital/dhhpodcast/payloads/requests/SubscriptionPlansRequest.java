package com.netshiftdigital.dhhpodcast.payloads.requests;

import lombok.Data;

@Data
public class SubscriptionPlansRequest {
    private String name;
    private Double amount;
    private String intervals;
    private String currency;
    private String paystackPlanCode;

    private Long planId;
}
