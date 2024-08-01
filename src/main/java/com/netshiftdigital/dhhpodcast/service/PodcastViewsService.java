package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastViewUpdateRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastViewsRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastViewResponse;

import java.util.List;

public interface PodcastViewsService {
    void createPodcastView(Long podcastId);
    PodcastViewResponse editPodcastView(Long id, PodcastViewUpdateRequest request);
    PodcastViewResponse findPodcastViewById(Long id);
    List<PodcastViewResponse> findAllPodcastViews();
    void deletePodcastViewById(Long id);
}