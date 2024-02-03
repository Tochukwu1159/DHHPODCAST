package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastCategoryDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastCategoryResponseDto;

import java.util.List;

public interface PodcastCategoryService {
    PodcastCategoryResponseDto createPodcastCategory(PodcastCategoryDto categoryDto);

    PodcastCategoryResponseDto updatePodcastCategory(Long categoryId, PodcastCategoryDto updatedCategoryDto);

    void deletePodcastCategory(Long categoryId);

    PodcastCategoryResponseDto getPodcastCategoryById(Long categoryId);

    List<PodcastCategoryResponseDto> getAllPodcastCategories();
}