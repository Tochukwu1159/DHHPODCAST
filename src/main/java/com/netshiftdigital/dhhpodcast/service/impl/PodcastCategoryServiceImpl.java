package com.netshiftdigital.dhhpodcast.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.*;
import com.netshiftdigital.dhhpodcast.models.Episodes;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.PodcastCategory;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastCategoryDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastCategoryResponseDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponse;
import com.netshiftdigital.dhhpodcast.repositories.PodcastCategoryRepository;
import com.netshiftdigital.dhhpodcast.repositories.SubscriptionPlanRepo;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.PodcastCategoryService;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import org.modelmapper.ModelMapper;
import com.netshiftdigital.dhhpodcast.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PodcastCategoryServiceImpl implements PodcastCategoryService {

    @Autowired
    private PodcastCategoryRepository podcastCategoryRepository;
    @Autowired
    private SubscriptionPlanRepo subscriptionPlanRepo;

    @Autowired
    private ModelMapper modelMapper; // You need to configure and inject ModelMapper
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserRepository userRepository;

    @Override
    public PodcastCategoryResponseDto createPodcastCategory(PodcastCategoryDto categoryDto) {
        try {
            System.out.println("hello");
            Optional<com.netshiftdigital.dhhpodcast.models.SubscriptionPlan> subscriptionPlan = subscriptionPlanRepo.findById(categoryDto.getUserPlan());
            if(!subscriptionPlan.isPresent()){
                throw new ResourceNotFoundException("Subscription plan not found");
            }
            MultipartFile imageFile = categoryDto.getFile();
            String name = categoryDto.getName();
            String plan = subscriptionPlan.get().getName();

            if (imageFile == null || imageFile.isEmpty()) {
                throw new ResourceNotFoundException("image File is empty or missing");
            }

            // Upload the image to Cloudinary
            Map<?, ?> uploadResultImage = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "folder", "images"
            ));

            String imageUrl = (String) uploadResultImage.get("secure_url");

            PodcastCategory podcastCategory = new PodcastCategory();
            podcastCategory.setName(name);
            podcastCategory.setSubscriptionPlan(plan);
            podcastCategory.setCoverPhoto(imageUrl);
            PodcastCategory savedPodcastCategory = podcastCategoryRepository.save(podcastCategory);

            return modelMapper.map(savedPodcastCategory, PodcastCategoryResponseDto.class);
        } catch (IOException e) {
            throw new FileProcessingException("Error processing file " +e);
        } catch (ResourceNotFoundException e) {

            throw new ResourceNotFoundException("Not found " +e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new CustomInternalServerException("An unexpected error occurred " +e);
        }
    }


    @Override
    public List<PodcastCategoryResponseDto> getAllPodcastCategories()  {
        List<PodcastCategory> allCategories = podcastCategoryRepository.findAll();

        // Map the list of PodcastCategory to a list of PodcastCategoryResponseDto
        return allCategories.stream()
                .map(category -> modelMapper.map(category, PodcastCategoryResponseDto.class))
                .collect(Collectors.toList());
    }

//
//    @Override
//    public List<PodcastCategoryResponseDto> getAllPodcastCategories() {
//        List<PodcastCategory> allCategories = podcastCategoryRepository.findAll();
//        return allCategories.stream()
//                .map(category -> {
//                    PodcastCategoryResponseDto categoryResponseDto = modelMapper.map(category, PodcastCategoryResponseDto.class);
//                    categoryResponseDto.setPodcastResponses(mapToPodcastResponses(category.getPodcastList()));
//                    return categoryResponseDto;
//                })
//                .collect(Collectors.toList());
//    }
//
//    private List<PodcastResponse> mapToPodcastResponses(List<Podcast> podcasts) {
//        return podcasts.stream()
//                .map(podcast -> PodcastResponse.builder()
//                        .id(podcast.getId())
//                        .title(podcast.getTitle())
//                        .coverPhoto(podcast.getCoverPhoto())
//                        .description(podcast.getDescription())
//                        .categoryId(podcast.getCategory().getId())
//                        .episodes(mapToEpisodeResponses(podcast.getEpisodes()))
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    private List<EpisodeResponse> mapToEpisodeResponses(List<Episodes> episodes) {
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


    @Override
    public PodcastCategoryResponseDto updatePodcastCategory(Long categoryId, PodcastCategoryDto updatedCategoryDto) {
        try {
            Optional<com.netshiftdigital.dhhpodcast.models.SubscriptionPlan> subscriptionPlan = subscriptionPlanRepo.findById(updatedCategoryDto.getUserPlan());
            String plan = subscriptionPlan.get().getName();
            // Check if the category with the given ID exists
            Optional<PodcastCategory> existingCategoryOptional = podcastCategoryRepository.findById(categoryId);
            if (existingCategoryOptional.isEmpty()) {
                return null;
            }

            MultipartFile imageFile = updatedCategoryDto.getFile();
            String name = updatedCategoryDto.getName();

            if (imageFile == null || imageFile.isEmpty()) {
                throw new ResourceNotFoundException("image File is empty or missing");
            }

            // Upload the image to Cloudinary
            Map<?, ?> uploadResultImage = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto",
                    "folder", "images"
            ));

            String imageUrl = (String) uploadResultImage.get("secure_url");

            PodcastCategory existingCategory = existingCategoryOptional.get();
            // Update the existing category with the new information
            existingCategory.setName(updatedCategoryDto.getName());
            existingCategory.setCoverPhoto(imageUrl);
            existingCategory.setSubscriptionPlan(plan);
            // You can update more fields if needed

            PodcastCategory updatedCategory = podcastCategoryRepository.save(existingCategory);
            return modelMapper.map(updatedCategory, PodcastCategoryResponseDto.class);
        } catch (IOException e) {
            // Handle IOException
            throw new FileProcessingException("Error processing file " +e);
        } catch (ResourceNotFoundException e) {
            // Handle ResourceNotFoundException
            throw new ResourceNotFoundException("Not found " +e);
        } catch (Exception e) {
            // Handle other exceptions
            throw new CustomInternalServerException("An unexpected error occurred "  +e);
        }
    }


    @Override
    public void deletePodcastCategory(Long categoryId) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName(); // Retrieve authenticated user email
            User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);
            if (admin == null) {
                throw new AuthenticationFailedException("Only admins can delete podcast categories.");
            }
            podcastCategoryRepository.deleteById(categoryId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException("Podcast category not found with ID: " + categoryId);
        } catch (DataAccessException ex) {
            throw new ServiceException("Failed to delete podcast category with ID: " + categoryId+ "" +ex);
        }
    }


    @Override
    public PodcastCategoryResponseDto getPodcastCategoryByName(String name) {
        PodcastCategory categoryOptional = podcastCategoryRepository.findByNameIgnoreCase(name);
        return modelMapper.map(categoryOptional, PodcastCategoryResponseDto.class);
    }

    @Override
    public PodcastCategoryResponseDto getPodcastCategoryById(Long id) {
        Optional<PodcastCategory> categoryOptional = podcastCategoryRepository.findById(id);
        return modelMapper.map(categoryOptional, PodcastCategoryResponseDto.class);
    }


}
