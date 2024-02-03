package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanResponseDto {
    private boolean status;
    private String message;
    private PlanData data;

    @Getter
    @Setter
    @ToString
    public static class PlanData {
        private Subscription[] subscriptions;
        private int integration;
        private String domain;
        private String name;
        private String planCode;
        private String description;
        private int amount;
        private String interval;
        private boolean sendInvoices;
        private boolean sendSms;
        private boolean hostedPage;
        private String hostedPageUrl;
        private String hostedPageSummary;
        private String currency;
        private int id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

}
