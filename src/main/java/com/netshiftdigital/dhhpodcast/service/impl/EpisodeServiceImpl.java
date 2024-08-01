package com.netshiftdigital.dhhpodcast.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;

import com.netshiftdigital.dhhpodcast.exceptions.ServiceException;
import com.netshiftdigital.dhhpodcast.models.Episodes;
        import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.payloads.requests.EpisodeRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponseWithPodcast;
import com.netshiftdigital.dhhpodcast.repositories.EpisodesRepository;
        import com.netshiftdigital.dhhpodcast.repositories.PodcastRepository;
import com.netshiftdigital.dhhpodcast.service.EpisodeService;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
        import java.util.stream.Collectors;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    private final EpisodesRepository episodesRepository;
    private final PodcastRepository podcastRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    public EpisodeServiceImpl(EpisodesRepository episodesRepository, PodcastRepository podcastRepository) {
        this.episodesRepository = episodesRepository;
        this.podcastRepository = podcastRepository;
    }

    public EpisodeResponse createEpisode(EpisodeRequest episodeRequest) {
        try {
            MultipartFile audioFile = episodeRequest.getAudioFile();
            Long podcastId = episodeRequest.getPodcastId();
            String title = episodeRequest.getTitle();
            String description = episodeRequest.getDescription();
            // Check if the podcast exists
            Optional<Podcast> podcastOptional = podcastRepository.findById(podcastId);
            if (podcastOptional.isEmpty()) {
                throw new ResourceNotFoundException("Podcast not found with id: " + episodeRequest.getPodcastId());
            }

            Podcast podcast = podcastOptional.get();
            ;
        if (audioFile == null || audioFile.isEmpty()) {
            throw new ResourceNotFoundException("Audio file is empty or missing");
        }
        //upload audio to cloudinary
        Map uploadResultAudio = cloudinary.uploader().upload(audioFile.getBytes(), ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "audio"
        ));
        String audioUrl = (String) uploadResultAudio.get("secure_url");


            // Create new episode
            Episodes episode = new Episodes();
            episode.setTitle(title);
            episode.setDescription(description);
            episode.setAudioUrl(audioUrl);
            episode.setPodcast(podcast);

            // Save episode
            Episodes savedEpisode = episodesRepository.save(episode);
            return EpisodeResponse.builder()
                    .podcastId(savedEpisode.getPodcast().getId())
                    .id(savedEpisode.getId())
                    .audioUrl(savedEpisode.getAudioUrl())
                    .description(savedEpisode.getDescription())
                    .title(savedEpisode.getTitle())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Failed to create episode: " + e.getMessage());
        }
    }

    public EpisodeResponse updateEpisode(Long episodeId, EpisodeRequest episodeRequest) {
        try {
            MultipartFile audioFile = episodeRequest.getAudioFile();
            String title = episodeRequest.getTitle();
            String description = episodeRequest.getDescription();
            Optional<Episodes> episodeOptional = episodesRepository.findById(episodeId);
            if (episodeOptional.isEmpty()) {
                throw new ResourceNotFoundException("Episode not found with id: " + episodeId);
            }

            Episodes episode = episodeOptional.get();

        // Validate the file and speaker request here
        if (audioFile == null || audioFile.isEmpty()) {
            throw new ResourceNotFoundException("Audio file is empty or missing");
        }

        //upload audio to cloudinary
        Map uploadResultAudio = cloudinary.uploader().upload(audioFile.getBytes(), ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "audio"
        ));

        String audioUrl = (String) uploadResultAudio.get("secure_url");

            // Update episode details
            episode.setTitle(title);
            episode.setDescription(description);
            episode.setAudioUrl(audioUrl);

            // Save updated episode
            Episodes updatedEpisode = episodesRepository.save(episode);

            // Map to EpisodeResponse
            return new EpisodeResponse(updatedEpisode.getId(), updatedEpisode.getTitle(), updatedEpisode.getDescription(),
                    updatedEpisode.getAudioUrl(), updatedEpisode.getPodcast().getId(), updatedEpisode.getCreatedDate());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update episode: " + e.getMessage());
        }
    }

    public List<EpisodeResponse> findAllEpisodes() {
        try {
            List<Episodes> episodes = episodesRepository.findAll();
            return episodes.stream().map(this::mapToEpisodeResponse).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve episodes" + " "+ e);
        }
    }  ;

//    public  List<EpisodeResponse> findAllEpisodesByPodcastId(Long podcastId) {
//        try {
//            Optional<Podcast> podcastOptional = podcastRepository.findById(podcastId);
//            if (podcastOptional.isEmpty()) {
//                throw new ResourceNotFoundException("Podcast not found with id: " + podcastId);
//            }
//            List<Episodes> episodes = episodesRepository.findByPodcast(podcastOptional.get());
//            return episodes.stream().map(this::mapToEpisodeResponse).collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new ServiceException("Failed to retrieve episodes" + " "+ e);
//        }
//    }
public List<EpisodeResponseWithPodcast> findAllEpisodesByPodcastId(Long podcastId) {
    try {
        Optional<Podcast> podcastOptional = podcastRepository.findById(podcastId);
        if (podcastOptional.isEmpty()) {
            throw new ResourceNotFoundException("Podcast not found with id: " + podcastId);
        }
        Podcast podcast = podcastOptional.get();
        List<Episodes> episodes = episodesRepository.findByPodcast(podcast);

        // Map episodes to EpisodeResponse
        EpisodeResponseWithPodcast episodeResponse = mapToEpisodeResponse(podcast, episodes);
        return List.of(episodeResponse);
    } catch (Exception e) {
        throw new ServiceException("Failed to retrieve episodes "+ e);
    }
    }

    private EpisodeResponseWithPodcast mapToEpisodeResponse(Podcast podcast, List<Episodes> episodes) {
        List<EpisodeResponseWithPodcast.Episodes> episodeList = episodes.stream()
                .map(episode -> EpisodeResponseWithPodcast.Episodes.builder()
                        .id(episode.getId())
                        .title(episode.getTitle())
                        .localDateTime(episode.getCreatedDate())
                        .description(episode.getDescription())
                        .audioUrl(episode.getAudioUrl())
                        .podcastId(episode.getPodcast().getId())
                        .build())
                .collect(Collectors.toList());

        return EpisodeResponseWithPodcast.builder()
                .podcastId(podcast.getId())
                .title(podcast.getTitle())
                .createdAt(podcast.getCreatedDate())
                .description(podcast.getDescription())
                .coverPhoto(podcast.getCoverPhoto())
                .episodes(episodeList)
                .build();
    }




    public EpisodeResponse findEpisodeById(Long episodeId) {
        try {
            Optional<Episodes> episodeOptional = episodesRepository.findById(episodeId);
            if (episodeOptional.isEmpty()) {
                throw new ResourceNotFoundException("Episode not found with id: " + episodeId);
            }
            return mapToEpisodeResponse(episodeOptional.get());
        } catch (Exception e) {
            throw new ServiceException("Failed to retrieve episode with id: " + episodeId + " "+ e);
        }
    }

    public void deleteEpisode(Long episodeId) {
        try {
            Optional<Episodes> episodeOptional = episodesRepository.findById(episodeId);
            if (episodeOptional.isEmpty()) {
                throw new ResourceNotFoundException("Episode not found with id: " + episodeId);
            }
            episodesRepository.delete(episodeOptional.get());
        } catch (Exception e) {
            throw new ServiceException("Failed to delete episode with id: " + episodeId + " "+ e);
        }
    }

    private EpisodeResponse mapToEpisodeResponse(Episodes episode) {
        return new EpisodeResponse(episode.getId(), episode.getTitle(),
                episode.getDescription(), episode.getAudioUrl(), episode.getPodcast().getId(), episode.getCreatedDate());
    }
}
