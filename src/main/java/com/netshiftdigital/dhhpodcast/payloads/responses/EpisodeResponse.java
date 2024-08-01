package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EpisodeResponse {

    private Long id;
    private String title;
    private String description;
    private String audioUrl;
    private Long podcastId;
    private LocalDateTime localDateTime;

    // Constructors, getters, and setters
}
