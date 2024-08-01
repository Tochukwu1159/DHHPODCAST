package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PodcastCategoryResponseDto {
    private Long id;
    private String name;
    private SubscriptionPlan subscriptionPlan;
    private String coverPhoto;

    List<PodcastResponse> podcastResponses;
}
