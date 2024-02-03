package com.netshiftdigital.dhhpodcast.service;


import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponseDto;

import java.util.List;

public interface PodcastService {
    PodcastResponseDto createPodcast(
            PodcastDto podcastDto) ;
    Podcast editPodcast(Long id, Podcast podcast);
    List<Podcast> findAllPodcasts();
    void deletePodcast(Long id);
}