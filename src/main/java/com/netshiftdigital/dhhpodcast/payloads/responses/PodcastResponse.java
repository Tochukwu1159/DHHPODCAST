package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PodcastResponse {

    private Long id;
    private String title;
    private String coverPhoto;
    private String description;
    private Long categoryId;

    private boolean isFavorite;


    private LocalDateTime localDate;

    private int totalViews;


}
