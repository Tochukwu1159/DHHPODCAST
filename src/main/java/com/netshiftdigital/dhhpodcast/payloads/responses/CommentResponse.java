package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private Long podcastId;
    private Long commentId;
    private Long profileId;
    private String profileName;

    private String content;
}