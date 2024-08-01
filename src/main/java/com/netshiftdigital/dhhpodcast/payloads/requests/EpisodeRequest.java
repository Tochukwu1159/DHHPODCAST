package com.netshiftdigital.dhhpodcast.payloads.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EpisodeRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotNull(message = "Podcast ID is mandatory")
    private Long podcastId;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotNull(message = "Audio file is required")
    private MultipartFile audioFile;

    // Constructors, getters, and setters
}
