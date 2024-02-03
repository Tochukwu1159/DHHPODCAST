package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public   class PayStackData{
        private Long integration;
        private BigDecimal amount;
        private String name;
        private String domain;
        @JsonProperty("invoice_limit")
        private Long invoiceLimit;
        private String interval;
        private Long id;
        @JsonProperty("send_invoices")
        private Boolean sendInvoices;
    @JsonProperty("send_sms")
    private Boolean sendSms;
        @JsonProperty("hosted_page")
        private Boolean hostedPage;
        private Boolean migrate;
        @JsonProperty("is_archived")
        private Boolean isArchived;
    @JsonProperty("email_token")
    private String emailToken;

    @JsonProperty("subscription_code")
    private String subscriptionCode;
    @JsonProperty("plan_code")
    private String planCode;

    private String currency;


}
