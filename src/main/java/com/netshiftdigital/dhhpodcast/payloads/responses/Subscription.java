package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.Data;

@Data
public class Subscription {
    private String name;
    private double amount;
    private String interval;
    private int integration;
    private String domain;
    private String planCode;
    private boolean sendInvoices;
    private boolean sendSms;
    private boolean hostedPage;
    private String currency;
    private int id;
    private String createdAt;
    private String updatedAt;
}
