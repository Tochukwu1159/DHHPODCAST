package com.netshiftdigital.dhhpodcast.payloads.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PayStackTransactionRequestDto {
    @JsonProperty("amount")
    private int amount;

    @JsonProperty("email")
    private String email;

    @JsonProperty("reference")
    private String reference;
}
