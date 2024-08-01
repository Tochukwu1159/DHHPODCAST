package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PodcastViewsRequest {
    @NotNull(message = "Podcast ID is mandatory")
    private Long podcastId;
    // Add other properties as needed
}
