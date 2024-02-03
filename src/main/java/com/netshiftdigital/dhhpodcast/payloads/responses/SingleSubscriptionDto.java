package com.netshiftdigital.dhhpodcast.payloads.responses;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleSubscriptionDto {
    private boolean status;
    private String message;
    private SubscriptionData data;

    // Getters and setters
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SubscriptionData {
        private List<Invoice> invoices;
        private Customer customer;
        private Plan plan;
        private int integration;
        private Authorization authorization;
        private String domain;
        private long start;
        private String status;
        private int quantity;
        private int amount;
        private String subscriptionCode;
        private String emailToken;
        private String easyCronId;
        private String cronExpression;
        private String nextPaymentDate;
        private String openInvoice;
        private int id;
        private String createdAt;
        private String updatedAt;

        // Getters and setters
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Invoice {
        // Define fields for Invoice if needed
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Customer {
        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String email;
        private String phone;
        private Metadata metadata;
        private String domain;

        @JsonProperty("customer_code")
        private String customerCode;

        private int id;
        private int integration;

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;

        // Getters and setters
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Metadata {
        private List<Photo> photos;

        // Getters and setters
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Photo {
        private String type;
        private String typeId;
        private String typeName;
        private String url;
        private boolean isPrimary;

        // Getters and setters
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Plan {
        private String domain;
        private String name;

        @JsonProperty("plan_code")
        private String planCode;

        private String description;
        private int amount;
        private String interval;

        @JsonProperty("send_invoices")
        private boolean sendInvoices;

        @JsonProperty("send_sms")
        private boolean sendSms;

        @JsonProperty("hosted_page")
        private boolean hostedPage;

        @JsonProperty("hosted_page_url")
        private String hostedPageUrl;

        @JsonProperty("hosted_page_summary")
        private String hostedPageSummary;

        private String currency;
        private int id;
        private int integration;

        @JsonProperty("createdAt")
        private String createdAt;

        @JsonProperty("updatedAt")
        private String updatedAt;

        // Getters and setters
    }
    @Getter
    @Setter
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)

    public static class Authorization {
        @JsonProperty("authorization_code")
        private String authorizationCode;

        private String bin;
        private String last4;

        @JsonProperty("exp_month")
        private String expMonth;

        @JsonProperty("exp_year")
        private String expYear;

        private String channel;

        @JsonProperty("card_type")
        private String cardType;

        private String bank;

        @JsonProperty("country_code")
        private String countryCode;

        private String brand;

        private boolean reusable;

        private String signature;

        @JsonProperty("account_name")
        private String accountName;

        // Getters and setters
    }
}
