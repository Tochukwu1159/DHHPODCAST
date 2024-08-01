package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PodcastViewUpdateRequest {

    @NotNull(message = "Podcast ID is mandatory")
    private Long podcastId;

    @Min(value = 0, message = "Views cannot be negative")
    private int views;
}