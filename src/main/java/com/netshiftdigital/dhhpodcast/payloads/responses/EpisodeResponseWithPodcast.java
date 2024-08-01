package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeResponseWithPodcast {

    private Long podcastId;
    private String title;
    private String description;
    private String coverPhoto;
    private LocalDateTime createdAt;
    private List<Episodes> episodes;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Episodes {

        private Long id;
        private String title;
        private String description;
        private String audioUrl;
        private Long podcastId;
        private LocalDateTime localDateTime;

        // Constructors, getters, and setters
    }

}

