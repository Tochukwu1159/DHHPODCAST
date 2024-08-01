package com.netshiftdigital.dhhpodcast.service;


import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponseList;

import java.io.IOException;
import java.util.List;

public interface PodcastService {
    PodcastResponse createPodcast(PodcastRequest request) throws IOException;
    PodcastResponse editPodcast(Long id, PodcastRequest request) throws IOException;
    PodcastResponseList findPodcastByName(String title);
    void deletePodcast(Long id);
    PodcastResponse findPodcastById(Long id);
    List<PodcastResponse> findAllPodcasts();

    PodcastResponseList findPodcastByCategoryName(String name);
}