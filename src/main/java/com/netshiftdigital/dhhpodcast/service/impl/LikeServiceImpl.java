package com.netshiftdigital.dhhpodcast.service.impl;


import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.models.*;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.LikeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponse;
import com.netshiftdigital.dhhpodcast.repositories.*;
import com.netshiftdigital.dhhpodcast.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private PodcastViewsRepository podcastViewsRepository;

    public String togglePodcastLike(Long podcastId) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new ResourceNotFoundException("User not found");
        }

        User user = userOptional.get();
        Profile profile = userProfileRepository.findByUser(user);
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found");
        }

        Optional<Podcast> podcastOptional = podcastRepository.findById(podcastId);
        if (!podcastOptional.isPresent()) {
            throw new ResourceNotFoundException("Podcast not found");
        }

        Podcast podcast = podcastOptional.get();
        if (podcast.isFavorite()) {
            // Remove from favorites
            podcast.setFavorite(false);
            podcastRepository.save(podcast);
            return "Podcast successfully removed from favorites";
        } else {
            // Add to favorites
            podcast.setFavorite(true);
            podcastRepository.save(podcast);
            return "Podcast successfully added to favorites";
        }
    }

    public LikeResponse allFavoritePodcasts() {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found");
        }

        Profile profile = userProfileRepository.findByUser(user.get());
        if (profile == null) {
            throw new ResourceNotFoundException("Profile not found");
        }

        List<Podcast> podcastList = podcastRepository.findByIsFavoriteTrue();
        List<PodcastResponse> podcastResponses = podcastList.stream()
                .map(this::mapToPodcastResponseWithViews)
                .collect(Collectors.toList());

        return new LikeResponse(podcastResponses);
    }

    private PodcastResponse mapToPodcastResponseWithViews(Podcast podcast) {
        PodcastResponse response = new PodcastResponse();
        response.setId(podcast.getId());
        response.setTitle(podcast.getTitle());
        response.setFavorite(podcast.isFavorite());
        response.setCoverPhoto(podcast.getCoverPhoto());
        response.setDescription(podcast.getDescription());
        response.setCategoryId(podcast.getCategory().getId());
        int totalViews = calculateTotalViews(podcast);
        response.setTotalViews(totalViews);
        return response;
    }

    private int calculateTotalViews(Podcast podcast) {
        List<PodcastViews> podcastViewsList = podcastViewsRepository.findByPodcast(podcast);
        return podcastViewsList.stream()
                .mapToInt(PodcastViews::getViews)
                .sum();
    }
}


//    @Override
//    public LikeResponse allFavoritePodcast() {
//
//        List<Likes> likedPodcasts = likeRepository.findByProfile(profile);
//        List<PodcastResponse> podcastResponses = likedPodcasts.stream()
//                .map(like -> mapToPodcastResponseWithViews(like.getPodcast()))
//                .collect(Collectors.toList());
//        return new LikeResponse(podcastResponses);
//    }








//    private PodcastResponse mapToPodcastResponse(Podcast podcast) {
//        return PodcastResponse.builder()
//                .id(podcast.getId())
//                .title(podcast.getTitle())
//                .coverPhoto(podcast.getCoverPhoto())
//                .description(podcast.getDescription())
//                .categoryId(podcast.getCategory().getId())
////                .episodes(mapToEpisodeResponse(podcast.getEpisodes()))
//                .build();
//    }

//    private List<EpisodeResponse> mapToEpisodeResponse(List<Episodes> episodes) {
//        return episodes.stream()
//                .map(episode -> EpisodeResponse.builder()
//                        .id(episode.getId())
//                        .title(episode.getTitle())
//                        .description(episode.getDescription())
//                        .audioUrl(episode.getAudioUrl())
//                        .podcastId(episode.getPodcast().getId())
//                        .build())
//                .collect(Collectors.toList());
//    }
