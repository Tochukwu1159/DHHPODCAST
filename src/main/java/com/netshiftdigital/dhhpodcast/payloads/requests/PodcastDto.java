package com.netshiftdigital.dhhpodcast.payloads.requests;

import lombok.Data;

@Data
public class PodcastDto {
    private String title;
    private String body;
    private String categoryName;
    // Add other fields as needed
}