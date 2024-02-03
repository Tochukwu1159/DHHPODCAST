package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Subscribe {
    private int customer;
    private int plan;
    private int integration;
    private String domain;
    private long start;
    private String status;
    private int quantity;
    private int amount;
    private String subscriptionCode;
    private String emailToken;
//    private Authorization authorization;
    private Integer easyCronId;
    private String cronExpression;
    private String nextPaymentDate;
    private Object openInvoice; // Adjust the type based on the actual data type

    // Getters and setters
}
