package com.netshiftdigital.dhhpodcast.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodcastResponseList {
    private List<PodcastResponse> podcast;
}