package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Likes;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    boolean existsByUserAndPodcast(User user, Podcast podcast);
    void deleteByUserIdAndPodcastId(Long userId,Long podcastId);

    List<Likes> findByPodcastId(Long podcastId);
}
