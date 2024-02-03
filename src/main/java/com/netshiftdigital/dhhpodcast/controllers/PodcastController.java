package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponseDto;
import com.netshiftdigital.dhhpodcast.service.PodcastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcasts")
public class PodcastController {
    @Autowired
    private PodcastService podcastService;

    @PostMapping
    public ResponseEntity<PodcastResponseDto> createPodcast(@RequestBody PodcastDto podcast) {
        PodcastResponseDto createdPodcast = podcastService.createPodcast(podcast);
        return new ResponseEntity<>(createdPodcast, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Podcast> editPodcast(@PathVariable Long id, @RequestBody Podcast updatedPodcast) {
        Podcast editedPodcast = podcastService.editPodcast(id, updatedPodcast);
        if (editedPodcast != null) {
            return ResponseEntity.ok(editedPodcast);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Podcast>> getAllPodcasts() {
        List<Podcast> podcasts = podcastService.findAllPodcasts();
        return ResponseEntity.ok(podcasts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcast(@PathVariable Long id) {
        podcastService.deletePodcast(id);
        return ResponseEntity.noContent().build();
    }
}

