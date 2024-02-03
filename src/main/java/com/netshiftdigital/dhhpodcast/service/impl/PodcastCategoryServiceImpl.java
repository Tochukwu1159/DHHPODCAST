package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.models.PodcastCategory;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastCategoryDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastCategoryResponseDto;
import com.netshiftdigital.dhhpodcast.repositories.PodcastCategoryRepository;
import com.netshiftdigital.dhhpodcast.service.PodcastCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PodcastCategoryServiceImpl implements PodcastCategoryService {

    @Autowired
    private PodcastCategoryRepository podcastCategoryRepository;

    @Autowired
    private ModelMapper modelMapper; // You need to configure and inject ModelMapper

    @Override
    public PodcastCategoryResponseDto createPodcastCategory(PodcastCategoryDto categoryDto) {
        // Map PodcastCategoryDto to PodcastCategory
        PodcastCategory podcastCategory = modelMapper.map(categoryDto, PodcastCategory.class);

        // Save the podcast category
        PodcastCategory savedCategory = podcastCategoryRepository.save(podcastCategory);

        // Map the saved PodcastCategory to PodcastCategoryResponseDto
        return modelMapper.map(savedCategory, PodcastCategoryResponseDto.class);
    }

    @Override
    public List<PodcastCategoryResponseDto> getAllPodcastCategories()  {
        List<PodcastCategory> allCategories = podcastCategoryRepository.findAll();

        // Map the list of PodcastCategory to a list of PodcastCategoryResponseDto
        return allCategories.stream()
                .map(category -> modelMapper.map(category, PodcastCategoryResponseDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public PodcastCategoryResponseDto updatePodcastCategory(Long categoryId, PodcastCategoryDto updatedCategoryDto) {
        // Check if the category with the given ID exists
        Optional<PodcastCategory> existingCategoryOptional = podcastCategoryRepository.findById(categoryId);
        if (existingCategoryOptional.isEmpty()) {
            // Handle the case where the category is not found
            // You may throw an exception or return an error response
            // For simplicity, let's return null here
            return null;
        }

        PodcastCategory existingCategory = existingCategoryOptional.get();
        // Update the existing category with the new information
        existingCategory.setName(updatedCategoryDto.getName());
        // You can update more fields if needed

        PodcastCategory updatedCategory = podcastCategoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, PodcastCategoryResponseDto.class);
    }

    @Override
    public void deletePodcastCategory(Long categoryId) {
        podcastCategoryRepository.deleteById(categoryId);
    }

    @Override
    public PodcastCategoryResponseDto getPodcastCategoryById(Long categoryId) {
        Optional<PodcastCategory> categoryOptional = podcastCategoryRepository.findById(categoryId);
        return categoryOptional.map(category -> modelMapper.map(category, PodcastCategoryResponseDto.class)).orElse(null);
    }


}
