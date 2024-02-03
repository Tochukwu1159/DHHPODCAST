package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionResponseDto {

    private boolean status;

    private String message;

    private SubscriptionData data;

    private String type;
    private String code;
    private Meta meta;
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta{
        private String nextStep;
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SubscriptionData {

        @JsonProperty("customer")
        private long customer;

        @JsonProperty("plan")
        private long plan;

        @JsonProperty("integration")
        private long integration;

        @JsonProperty("domain")
        private String domain;

        @JsonProperty("start")
        private long start;

        @JsonProperty("status")
        private String status;

        @JsonProperty("quantity")
        private int quantity;

        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("authorization")
        private Long authorization;
        @JsonProperty("invoice_limit")
        private String invoiceLimit;
        @JsonProperty("split_code")
        private String splitCode;

        @JsonProperty("subscription_code")
        private String subscriptionCode;

        @JsonProperty("email_token")
        private String emailToken;

        @JsonProperty("id")
        private long id;
        @JsonProperty("cancelledAt")
        private String cancelledAt;


        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;

        @JsonProperty("cron_expression")
        private String cronExpression;
        @JsonProperty("next_payment_date")
        private LocalDateTime nextPaymentDate;
    }
}