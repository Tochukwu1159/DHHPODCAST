package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastCategoryDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastCategoryResponseDto;
import com.netshiftdigital.dhhpodcast.service.PodcastCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/podcast-categories")
public class PodcastCategoryController {

    @Autowired
    private PodcastCategoryService categoryService;

    @PostMapping
    public ResponseEntity<PodcastCategoryResponseDto> createPodcastCategory(
            @RequestParam("name") @Valid String name,
            @RequestParam("imageFile") @Valid MultipartFile file,
            @RequestParam(value = "userPlan") @Valid Long userPlan
            ) throws IOException {
        PodcastCategoryDto formData = new PodcastCategoryDto( name,userPlan, file);
        return new ResponseEntity<>(categoryService.createPodcastCategory(formData), HttpStatus.OK);

    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<PodcastCategoryResponseDto> updatePodcastCategory(
            @PathVariable Long categoryId,
            @RequestParam("name") @Valid String name,
            @RequestParam("imageFile") @Valid MultipartFile file,
            @RequestParam("userPlan") @Valid Long userPlan
    ) throws IOException {
            PodcastCategoryDto formData = new PodcastCategoryDto( name,userPlan, file);
        PodcastCategoryResponseDto updatedCategory = categoryService.updatePodcastCategory(categoryId, formData);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deletePodcastCategory(@PathVariable Long categoryId) {
        categoryService.deletePodcastCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{name}")
    public ResponseEntity<PodcastCategoryResponseDto> getPodcastCategoryByNme(@PathVariable String name) {
        PodcastCategoryResponseDto category = categoryService.getPodcastCategoryByName(name);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/single/{id}")
    public ResponseEntity<PodcastCategoryResponseDto> getPodcastCategoryById(@PathVariable Long id) {
        PodcastCategoryResponseDto category = categoryService.getPodcastCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PodcastCategoryResponseDto>> getAllPodcastCategories() {
        List<PodcastCategoryResponseDto> allCategories = categoryService.getAllPodcastCategories();
        return ResponseEntity.ok(allCategories);
    }
}
