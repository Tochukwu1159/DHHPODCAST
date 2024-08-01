package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlanCodeDto {
    @NotNull(message = "plan code cannot be null")
    private String planCode;
}
