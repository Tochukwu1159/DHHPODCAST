package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastCategoryDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastCategoryResponseDto;

import java.io.IOException;
import java.util.List;

public interface PodcastCategoryService {
    PodcastCategoryResponseDto createPodcastCategory(PodcastCategoryDto categoryDto) throws IOException;

    PodcastCategoryResponseDto updatePodcastCategory(Long categoryId, PodcastCategoryDto updatedCategoryDto) throws IOException;

    void deletePodcastCategory(Long categoryId);

    PodcastCategoryResponseDto getPodcastCategoryByName(String name);

    PodcastCategoryResponseDto getPodcastCategoryById(Long id);

    List<PodcastCategoryResponseDto> getAllPodcastCategories();
}