package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.exceptions.PodcastCategoryNotFoundException;
import com.netshiftdigital.dhhpodcast.exceptions.ServiceException;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.PodcastCategory;
import com.netshiftdigital.dhhpodcast.models.PodcastViews;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastViewUpdateRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastViewsRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastViewResponse;
import com.netshiftdigital.dhhpodcast.repositories.PodcastRepository;
import com.netshiftdigital.dhhpodcast.repositories.PodcastViewsRepository;
import com.netshiftdigital.dhhpodcast.service.PodcastViewsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PodcastViewsServiceImpl implements PodcastViewsService {

    @Autowired
    private PodcastViewsRepository podcastViewsRepository;
    @Autowired
    private PodcastRepository podcastRepository;

    @Override
    public void createPodcastView(Long podcastId) {
        try {
            Optional<Podcast> existingPodcast = podcastRepository.findById(podcastId);
            if (existingPodcast == null) {
                throw new PodcastCategoryNotFoundException("Podcast  with Id not found: " + podcastId);
            }
            PodcastViews podcastViews = new PodcastViews();
            podcastViews.setViews(podcastViews.getViews() + 1);
            podcastViews.setPodcast(existingPodcast.get());

            PodcastViews savedPodcastViews = podcastViewsRepository.save(podcastViews);
        } catch (Exception ex) {
            // Log the exception
            throw new ServiceException("Failed to create podcast view " +ex);
        }
    }

    @Override
    public PodcastViewResponse editPodcastView(Long id, PodcastViewUpdateRequest request) {
        try {
            PodcastViews podcastViews = podcastViewsRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("PodcastView with id " + id + " not found"));

            podcastViews.setViews(request.getViews());
            // Update other properties as needed

            PodcastViews updatedPodcastViews = podcastViewsRepository.save(podcastViews);
            return mapToPodcastViewResponse(updatedPodcastViews);
        } catch (Exception ex) {
            // Log the exception
            throw new ServiceException("Failed to edit podcast view with id " + id + " " +ex);
        }
    }

    @Override
    public PodcastViewResponse findPodcastViewById(Long podcastId) {
        try {
            List<PodcastViews> podcastViewsList = podcastViewsRepository.findByPodcastId(podcastId);
            int totalViews = podcastViewsList.stream()
                    .mapToInt(PodcastViews::getViews)
                    .sum();
            Podcast podcast = podcastRepository.findById(podcastId)
                    .orElseThrow(() -> new NotFoundException("Podcast not found with id: " + podcastId));

            return new PodcastViewResponse(
                    podcastId,
                    podcast.getTitle(),
                    podcast.getCoverPhoto(),
                    podcast.getDescription(),
                    totalViews
            );
        } catch (NotFoundException ex) {
            throw ex; // Re-throw the NotFoundException
        } catch (Exception ex) {
            // Log the exception
            throw new ServiceException("Failed to find podcast view for podcast with id " + podcastId + " " + ex);
        }
    }

    public List<PodcastViewResponse> findAllPodcastViews() {
        try {
            List<PodcastViews> podcastViewsList = podcastViewsRepository.findAll();

            Map<Long, Integer> totalViewsMap = podcastViewsList.stream()
                    .collect(Collectors.groupingBy(pv -> pv.getPodcast().getId(),
                            Collectors.summingInt(PodcastViews::getViews)));
            Map<Long, PodcastViewResponse> responseMap = new HashMap<>();
            for (PodcastViews podcastViews : podcastViewsList) {
                Long podcastId = podcastViews.getPodcast().getId();
                if (!responseMap.containsKey(podcastId)) {
                    PodcastViewResponse response = mapPodcastViewsToResponse(podcastViews, totalViewsMap);
                    responseMap.put(podcastId, response);
                }
            }
            return new ArrayList<>(responseMap.values());
        } catch (Exception ex) {
            // Log the exception
            throw new ServiceException("Failed to find all podcast views " + ex);
        }
    }


    @Override
    public void deletePodcastViewById(Long id) {
        try {
            if (!podcastViewsRepository.existsById(id)) {
                throw new EntityNotFoundException("PodcastView with id " + id + " not found");
            }
            podcastViewsRepository.deleteById(id);
        } catch (Exception ex) {
            // Log the exception
            throw new ServiceException("Failed to delete podcast view with id " + id + " " +ex);
        }
    }
    private PodcastViewResponse mapToPodcastViewResponse(PodcastViews podcastViews) {
        PodcastViewResponse response = new PodcastViewResponse();
        response.setPodcastId(podcastViews.getId());
        response.setTotalViews(podcastViews.getViews());
        // Map other properties as needed
        return response;
    }

    private PodcastViewResponse mapPodcastViewsToResponse(PodcastViews podcastViews, Map<Long, Integer> totalViewsMap) {
        PodcastViewResponse response = new PodcastViewResponse();
        response.setPodcastId(podcastViews.getPodcast().getId());
        response.setTitle(podcastViews.getPodcast().getTitle());
        response.setCoverPhoto(podcastViews.getPodcast().getCoverPhoto());
        response.setDescription(podcastViews.getPodcast().getDescription());
        int totalViews = totalViewsMap.getOrDefault(podcastViews.getPodcast().getId(), 0);
        response.setTotalViews(totalViews);
        return response;
    }
}


