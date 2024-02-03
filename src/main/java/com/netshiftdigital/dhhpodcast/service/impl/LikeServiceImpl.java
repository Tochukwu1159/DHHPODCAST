package com.netshiftdigital.dhhpodcast.service.impl;


import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.models.Likes;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.repositories.LikeRepository;
import com.netshiftdigital.dhhpodcast.repositories.PodcastRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PodcastRepository podcastRepository;

    @Override
    public Likes likePodcast(Long podcastId) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        Optional<Podcast> podcast = podcastRepository.findById(podcastId);
        // Check if the user has already liked the podcast
        if (!likeRepository.existsByUserAndPodcast(user.get(), podcast.get())) {
            Likes like = new Likes();
            like.setUser(user.get());
            like.setPodcast(podcast.get());
            return likeRepository.save(like);
        }
        return null;  // Return null or handle the case when the user has already liked the podcast
    }

    @Override
    public void unlikePodcast(Long userId, Long podcastId) {
        likeRepository.deleteByUserIdAndPodcastId(userId, podcastId);
    }

    @Override
    public List<Likes> getLikesByPodcast(Long podcastId) {
        return likeRepository.findByPodcastId(podcastId);
    }
}