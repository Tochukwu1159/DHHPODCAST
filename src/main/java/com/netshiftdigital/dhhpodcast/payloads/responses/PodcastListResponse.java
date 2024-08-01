package com.netshiftdigital.dhhpodcast.payloads.responses;

import java.util.List;

public class PodcastListResponse extends PodcastResponse {
    private List<PodcastResponse> podcasts;

    public PodcastListResponse(List<PodcastResponse> podcasts) {
        this.podcasts = podcasts;
    }

    public List<PodcastResponse> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(List<PodcastResponse> podcasts) {
        this.podcasts = podcasts;
    }
}
