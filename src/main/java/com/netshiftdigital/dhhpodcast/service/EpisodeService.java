package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.EpisodeRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponseWithPodcast;

import java.util.List;

public interface EpisodeService {
    EpisodeResponse createEpisode(EpisodeRequest episodeRequest);
    EpisodeResponse updateEpisode(Long episodeId, EpisodeRequest episodeRequest);
    List<EpisodeResponse> findAllEpisodes();
    EpisodeResponse findEpisodeById(Long episodeId);
    void deleteEpisode(Long episodeId);

    List<EpisodeResponseWithPodcast> findAllEpisodesByPodcastId(Long podcastId);
}
