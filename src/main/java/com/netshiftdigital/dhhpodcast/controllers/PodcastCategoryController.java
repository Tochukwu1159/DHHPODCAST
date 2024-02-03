package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastCategoryDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastCategoryResponseDto;
import com.netshiftdigital.dhhpodcast.service.PodcastCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcast-categories")
public class PodcastCategoryController {

    @Autowired
    private PodcastCategoryService categoryService;

    @PostMapping
    public ResponseEntity<PodcastCategoryResponseDto> createPodcastCategory(@RequestBody PodcastCategoryDto categoryDto) {
        PodcastCategoryResponseDto createdCategory = categoryService.createPodcastCategory(categoryDto);
        if (createdCategory != null) {
            return ResponseEntity.ok(createdCategory);
        } else {
            // Handle the case where the category already exists
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<PodcastCategoryResponseDto> updatePodcastCategory(
            @PathVariable Long categoryId,
            @RequestBody PodcastCategoryDto updatedCategoryDto) {
        PodcastCategoryResponseDto updatedCategory = categoryService.updatePodcastCategory(categoryId, updatedCategoryDto);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            // Handle the case where the category is not found
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deletePodcastCategory(@PathVariable Long categoryId) {
        categoryService.deletePodcastCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<PodcastCategoryResponseDto> getPodcastCategoryById(@PathVariable Long categoryId) {
        PodcastCategoryResponseDto category = categoryService.getPodcastCategoryById(categoryId);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            // Handle the case where the category is not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PodcastCategoryResponseDto>> getAllPodcastCategories() {
        List<PodcastCategoryResponseDto> allCategories = categoryService.getAllPodcastCategories();
        return ResponseEntity.ok(allCategories);
    }
}
