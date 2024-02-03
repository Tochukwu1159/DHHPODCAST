package com.netshiftdigital.dhhpodcast.payloads.requests;

import lombok.Data;

@Data
public class SubscriptionPlanDto {
        private String planCode;
        private String planName;
        private Double amount;
        private String interval;

        // getters and setters
    }

