package com.netshiftdigital.dhhpodcast.service;


import com.netshiftdigital.dhhpodcast.models.Likes;

import java.util.List;

public interface LikeService {
    Likes likePodcast(Long podcastId);
    void unlikePodcast(Long userId, Long podcastId);
    List<Likes> getLikesByPodcast(Long podcastId);
}