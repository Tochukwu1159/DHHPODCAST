package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.Data;

@Data
public class PodcastResponseDto {
    private String title;
    private String body;
    private String audioUrl;
    private String coverPhoto;
    // Add other fields as needed
}