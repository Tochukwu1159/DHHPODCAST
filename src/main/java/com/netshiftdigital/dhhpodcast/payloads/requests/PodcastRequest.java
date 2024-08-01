package com.netshiftdigital.dhhpodcast.payloads.requests;

import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodcastRequest {
    @NotNull(message = "Image of the podcast is required")
    @NotEmpty
    private MultipartFile file;  // Consider adding file type validation

    @Min(value = 1, message = "Category ID must be a positive integer (if provided)")
    private Long categoryId;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;
//    private List<EpisodeRequest> episodes;


}
