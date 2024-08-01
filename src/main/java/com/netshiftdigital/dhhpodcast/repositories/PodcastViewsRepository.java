package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.PodcastViews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PodcastViewsRepository extends JpaRepository<PodcastViews, Long> {
    List<PodcastViews> findByPodcastId(long podcastId);

    List<PodcastViews> findByPodcast(Podcast podcast);


}
