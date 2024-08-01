package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Likes;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.Profile;
import com.netshiftdigital.dhhpodcast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    boolean existsByProfileAndPodcast(Profile user, Podcast podcast);
    List<Likes> findByProfile(Profile profile);
    void deleteByProfileIdAndPodcastId(Long userId,Long podcastId);

    Optional<Likes> findByProfileAndPodcast(Profile userId, Podcast podcast);

    List<Likes> findByPodcastId(Long podcastId);
}
