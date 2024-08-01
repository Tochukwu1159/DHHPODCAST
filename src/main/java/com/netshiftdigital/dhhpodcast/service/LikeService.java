package com.netshiftdigital.dhhpodcast.service;


import com.netshiftdigital.dhhpodcast.models.Likes;
import com.netshiftdigital.dhhpodcast.payloads.responses.LikeResponse;

import java.util.List;

public interface LikeService {
    LikeResponse allFavoritePodcasts();
    String togglePodcastLike(Long podcastId);
}