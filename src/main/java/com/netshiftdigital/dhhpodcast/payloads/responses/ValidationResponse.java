package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationResponse {
    private boolean valid;
    private String message;
}
