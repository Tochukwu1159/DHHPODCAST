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
public class PayStackResponseObj {
    private boolean status;
    private String message;
    private List<PlanData> data;
    private Meta meta;

    // Getters and setters
}



