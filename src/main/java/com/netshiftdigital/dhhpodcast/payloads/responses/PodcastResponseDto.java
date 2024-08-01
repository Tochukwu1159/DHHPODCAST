package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PodcastResponseDto {
    private Long categoryId;
    private String title;


    private String description;
    private String coverPhoto;
    // Add other fields as needed
}