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
public class Authorization {
    private String authorizationCode;
    private String bin;
    private String last4;
    private String expMonth;
    private String expYear;
    private String channel;
    private String cardType;
    private String bank;
    private String countryCode;
    private String brand;
    private boolean reusable;
    private String signature;
    private String accountName;

    // Getters and setters
}
