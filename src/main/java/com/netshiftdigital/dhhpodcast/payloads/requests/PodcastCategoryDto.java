package com.netshiftdigital.dhhpodcast.payloads.requests;

import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodcastCategoryDto {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    // Optional userPlan validation (consider business logic)
    @NotNull(message = "User plan cannot be null")
    private Long userPlan;

    private MultipartFile file;  // Consider adding file type validation

}
