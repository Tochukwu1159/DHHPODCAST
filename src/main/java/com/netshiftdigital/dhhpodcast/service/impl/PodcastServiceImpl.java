package com.netshiftdigital.dhhpodcast.service.impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.netshiftdigital.dhhpodcast.exceptions.PodcastCategoryNotFoundException;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.PodcastCategory;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponseDto;
import com.netshiftdigital.dhhpodcast.repositories.PodcastCategoryRepository;
import com.netshiftdigital.dhhpodcast.repositories.PodcastRepository;
import com.netshiftdigital.dhhpodcast.service.PodcastService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PodcastServiceImpl implements PodcastService {
    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private PodcastCategoryRepository podcastCategoryRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PodcastResponseDto createPodcast(PodcastDto podcastDto) {
        PodcastCategory existingCategory = podcastCategoryRepository.findByName(podcastDto.getCategoryName());
        if (existingCategory == null) {
            throw new PodcastCategoryNotFoundException("Podcast category not found: " + podcastDto.getCategoryName());
        }
        // Map PodcastDto to Podcast
        Podcast podcast = modelMapper.map(podcastDto, Podcast.class);
        podcast.setCreatedDate(LocalDateTime.now()); // Set the createdDate to the current date/time
        podcast.setAudioUrl("https://mypodcats.com");
        podcast.setCategory(existingCategory); // Set the PodcastCategory

//        // Validate the file and speaker request here
//        if (podcastDto.getFile() == null || podcastDto.getFile().isEmpty()) {
//            throw new CustomNotFoundException("File is empty or missing");
//        }
//
//
//        // Upload the image to Cloudinary
//        Map<?, ?> uploadResult = cloudinary.uploader().upload(podcastDto.getFile().getBytes(), ObjectUtils.emptyMap());
//        String imageUrl = (String) uploadResult.get("secure_url");
//        podcast.setCoverPhoto(imageUrl);

        // Save the podcast
        Podcast savedPodcast = podcastRepository.save(podcast);

        // Map the saved Podcast to PodcastResponseDto
        return modelMapper.map(savedPodcast, PodcastResponseDto.class);
    }
    @Override
    public Podcast editPodcast(Long id, Podcast updatedPodcast) {
        Optional<Podcast> existingPodcast = podcastRepository.findById(id);
        if (existingPodcast.isPresent()) {
            Podcast podcast = existingPodcast.get();
            podcast.setTitle(updatedPodcast.getTitle());
            podcast.setCategory(updatedPodcast.getCategory());
            podcast.setBody(updatedPodcast.getBody());
            return podcastRepository.save(podcast);
        }
        return null; // Handle the case when the podcast is not found
    }

    @Override
    public List<Podcast> findAllPodcasts() {
        return podcastRepository.findAll();
    }

    @Override
    public void deletePodcast(Long id) {
        podcastRepository.deleteById(id);
    }
}