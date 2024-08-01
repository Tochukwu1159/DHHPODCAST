package com.netshiftdigital.dhhpodcast.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.models.*;
import com.netshiftdigital.dhhpodcast.payloads.requests.EpisodeRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastListResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponseList;
import com.netshiftdigital.dhhpodcast.repositories.*;
import com.netshiftdigital.dhhpodcast.service.PodcastService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PodcastServiceImpl implements PodcastService {

    @Autowired
    private PodcastRepository podcastRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EpisodesRepository episodeRepository;

    @Autowired
    private PodcastCategoryRepository podcastCategoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PodcastViewsRepository podcastViewsRepository;

    @Override
    public PodcastResponse createPodcast(PodcastRequest podcastRequest) throws IOException {
        try {
            PodcastCategory podcastCategory = podcastCategoryRepository.findById(podcastRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Podcast category with id " + podcastRequest.getDescription() + " not found"));
            if (podcastRequest.getFile() == null || podcastRequest.getFile().isEmpty()) {
                throw new ResourceNotFoundException("Image file is empty or missing");
            }
            // Upload the image to Cloudinary
            Map<?, ?> uploadResultImage = cloudinary.uploader().upload(podcastRequest.getFile().getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "folder", "images"
            ));
            String imageUrl = (String) uploadResultImage.get("secure_url");
            Podcast podcast = new Podcast();
            podcast.setTitle(podcastRequest.getTitle());
            podcast.setDescription(podcastRequest.getDescription());
            podcast.setCoverPhoto(imageUrl);
            podcast.setCategory(podcastCategory);
       Podcast savedPodcast =     podcastRepository.save(podcast);

            PodcastResponse response = modelMapper.map(savedPodcast, PodcastResponse.class);
            response.setCategoryId(savedPodcast.getCategory().getId());
            return response;
        } catch (IOException e) {
            // Handle IO exception
            throw new IOException("Error occurred while uploading files", e);
        } catch (EntityNotFoundException e) {
            // Handle entity not found exception
            throw new EntityNotFoundException("Error occurred while fetching podcast category", e);
        }
    }

    @Override
    public PodcastResponse editPodcast(Long id, PodcastRequest podcastRequest) throws IOException {
        try {
            Podcast podcast = podcastRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Podcast with id " + id + " not found"));
            if (podcastRequest.getFile() == null || podcastRequest.getFile().isEmpty()) {
                throw new ResourceNotFoundException("Image file is empty or missing");
            }
            // Upload the image to Cloudinary
            Map<?, ?> uploadResultImage = cloudinary.uploader().upload(podcastRequest.getFile().getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "folder", "images"
            ));
            String imageUrl = (String) uploadResultImage.get("secure_url");
            podcast.setTitle(podcastRequest.getTitle());
            podcast.setCoverPhoto(imageUrl);
            podcast.setDescription(podcastRequest.getDescription());
           Podcast updatedPodcast =  podcastRepository.save(podcast);
            PodcastResponse response = modelMapper.map(updatedPodcast, PodcastResponse.class);
            response.setCategoryId(updatedPodcast.getCategory().getId());
            return response;
        } catch (IOException e) {
            // Handle IO exception
            throw new IOException("Error occurred while uploading files", e);
        } catch (EntityNotFoundException e) {
            // Handle entity not found exception
            throw new EntityNotFoundException("Error occurred while fetching podcast", e);
        }
    }

    @Override
    public void deletePodcast(Long id) {
        try {
            if (!podcastRepository.existsById(id)) {
                throw new EntityNotFoundException("Podcast with id " + id + " not found");
            }
            podcastRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            // Handle entity not found exception
            throw new EntityNotFoundException("Error occurred while deleting podcast", e);
        }
    }

    @Override
    public PodcastResponse findPodcastById(Long id) {
        try {
            Podcast podcast = podcastRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Podcast with id " + id + " not found"));
            return mapToPodcastResponse(podcast);
        } catch (EntityNotFoundException e) {
            // Handle entity not found exception
            throw new EntityNotFoundException("Error occurred while fetching podcast", e);
        }
    }

    @Override
    public PodcastResponseList findPodcastByName(String title) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found, please login ");
        }

        List<Podcast> podcasts = podcastRepository.findAllByTitleContainingIgnoreCase(title);
        if (podcasts.isEmpty()) {
            throw new EntityNotFoundException("Podcast with title '" + title + "' not found");
        } else {
            List<PodcastResponse> responses = podcasts.stream()
                    .map(this::mapToPodcastResponse)
                    .collect(Collectors.toList());
            return new PodcastResponseList(responses);
        }
    }

    @Override
    public PodcastResponseList findPodcastByCategoryName(String name) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found, please login ");
        }

        List<Podcast> podcasts = podcastRepository.findAllByCategoryName(name);
        if (podcasts.isEmpty()) {
            throw new EntityNotFoundException("Podcast with title '" + name + "' not found");
        } else {
            List<PodcastResponse> responses = podcasts.stream()
                    .map(this::mapToPodcastResponse)
                    .collect(Collectors.toList());
            return new PodcastResponseList(responses);
        }
    }

//    @Override
//    public List<PodcastResponse> findAllPodcasts() {
//        try {
//            List<Podcast> podcasts = podcastRepository.findAll();
//            return podcasts.stream()
//                    .map(this::mapToPodcastResponse)
//                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            // Handle other exceptions
//            throw new RuntimeException("Error occurred while fetching podcasts", e);
//        }
//    }

    @Override
    public List<PodcastResponse> findAllPodcasts() {
        try {
            List<Podcast> podcasts = podcastRepository.findAll();
            Map<Long, Integer> totalViewsMap = calculateTotalViews();
            List<PodcastResponse> podcastResponses = podcasts.stream()
                    .map(podcast -> mapToPodcastResponse(podcast, totalViewsMap))
                    .collect(Collectors.toList());
            Collections.sort(podcastResponses, Comparator.comparingInt(PodcastResponse::getTotalViews).reversed());
            return podcastResponses;
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Error occurred while fetching podcasts", e);
        }
    }
    private Map<Long, Integer> calculateTotalViews() {
        List<PodcastViews> podcastViewsList = podcastViewsRepository.findAll();
        return podcastViewsList.stream()
                .collect(Collectors.groupingBy(
                        pv -> pv.getPodcast().getId(),
                        Collectors.summingInt(PodcastViews::getViews)
                ));
    }

    private PodcastResponse mapToPodcastResponse(Podcast podcast, Map<Long, Integer> totalViewsMap) {
        PodcastResponse response = new PodcastResponse();
        response.setId(podcast.getId());
        response.setTitle(podcast.getTitle());
        response.setCoverPhoto(podcast.getCoverPhoto());
        response.setDescription(podcast.getDescription());
        response.setLocalDate(podcast.getCreatedDate());
        response.setFavorite(podcast.isFavorite());
        response.setCategoryId(podcast.getCategory().getId());

        // Set the total views for the podcast
        int totalViews = totalViewsMap.getOrDefault(podcast.getId(), 0);
        response.setTotalViews(totalViews);

        // Add other properties as needed
        return response;
    }


    private PodcastResponse mapToPodcastResponse(Podcast podcast) {
        PodcastResponse response = new PodcastResponse();
        response.setId(podcast.getId());
        response.setTitle(podcast.getTitle());
        response.setCoverPhoto(podcast.getCoverPhoto());
        response.setLocalDate(podcast.getCreatedDate());
        response.setDescription(podcast.getDescription());
        response.setFavorite(podcast.isFavorite());
        response.setCategoryId(podcast.getCategory().getId());

        List<Episodes> episodes = podcast.getEpisodes();
        if (episodes != null) {
            List<EpisodeResponse> episodeResponses = new ArrayList<>();
            for (Episodes episode : episodes) {
                EpisodeResponse episodeResponse = new EpisodeResponse();
                episodeResponse.setId(episode.getId());
                episodeResponse.setTitle(episode.getTitle());
                episodeResponse.setLocalDateTime(episode.getCreatedDate());
                episodeResponse.setDescription(episode.getDescription());
                episodeResponse.setAudioUrl(episode.getAudioUrl());
                episodeResponse.setPodcastId(episode.getPodcast().getId());
                episodeResponses.add(episodeResponse);
//                response.setEpisodes(episodeResponses);
            }

        }
        return response;
    }
}
