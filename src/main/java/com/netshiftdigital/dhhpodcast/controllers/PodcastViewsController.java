package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastViewUpdateRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastViewsRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastViewResponse;
import com.netshiftdigital.dhhpodcast.service.PodcastViewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcast-views")
public class PodcastViewsController {

    @Autowired
    private PodcastViewsService podcastViewsService;

    @PostMapping("/{podcastId}")
    public ResponseEntity<Void> createPodcastView(@PathVariable  Long podcastId) {
        podcastViewsService.createPodcastView(podcastId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastViewResponse> editPodcastView(@PathVariable Long id, @RequestBody @Valid PodcastViewUpdateRequest request) {
        PodcastViewResponse response = podcastViewsService.editPodcastView(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastViewResponse> findPodcastViewById(@PathVariable Long id) {
        PodcastViewResponse response = podcastViewsService.findPodcastViewById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PodcastViewResponse>> findAllPodcastViews() {
        List<PodcastViewResponse> responses = podcastViewsService.findAllPodcastViews();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcastViewById(@PathVariable Long id) {
        podcastViewsService.deletePodcastViewById(id);
        return ResponseEntity.noContent().build();
    }
}

