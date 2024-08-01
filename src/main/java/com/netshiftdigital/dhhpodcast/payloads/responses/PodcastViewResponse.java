package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodcastViewResponse {
    private Long podcastId;
    private  String title;
    private String coverPhoto;
    private String  description;
    private int totalViews;    // Add other properties as needed
}
