package com.netshiftdigital.dhhpodcast.payloads.responses;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanData {
    private List<Subscribe> subscriptions;
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
    private String createdAt;
    private String updatedAt;

    // Getters and setters
}
