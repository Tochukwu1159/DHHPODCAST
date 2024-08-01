package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.exceptions.PodcastCategoryNotFoundException;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.payloads.requests.EpisodeRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.EpisodeResponseWithPodcast;
import com.netshiftdigital.dhhpodcast.service.EpisodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcast/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @PostMapping
    public ResponseEntity<EpisodeResponse> createEpisode(
               @RequestParam("title") @Valid String title,
              @RequestParam("podcastId") @Valid Long podcastId,
              @RequestParam("audioFile") @Valid MultipartFile audioFile,
              @RequestParam("description") @Valid String description
            ) {
        EpisodeRequest formData = new EpisodeRequest(title, podcastId,description,audioFile);
        EpisodeResponse createdEpisode = episodeService.createEpisode(formData);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEpisode);

    }

    @PutMapping("/{episodeId}/update")
    public ResponseEntity<EpisodeResponse> updateEpisode(@PathVariable Long episodeId,
                                                          @RequestParam("title") @Valid String title,
                                                          @RequestParam("podcastId")  @Valid Long podcastId,
                                                          @RequestParam("audioFile") @Valid MultipartFile audio,
                                                          @RequestParam("description") @Valid String description
    ) {
        EpisodeRequest formData = new EpisodeRequest( title, podcastId,description,audio);
            EpisodeResponse updatedEpisode = episodeService.updateEpisode(episodeId, formData);
            return ResponseEntity.ok(updatedEpisode);
    }
    @GetMapping
    public ResponseEntity<List<EpisodeResponseWithPodcast>> getAllEpisodesByPodcatsId(@RequestParam Long podcastId) {
        List<EpisodeResponseWithPodcast> allEpisodes = episodeService.findAllEpisodesByPodcastId( podcastId);
        return ResponseEntity.ok(allEpisodes);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EpisodeResponse>> getAllEpisodes() {
        List<EpisodeResponse> allEpisodes = episodeService.findAllEpisodes();
        return ResponseEntity.ok(allEpisodes);
    }

    @GetMapping("/{episodeId}")
    public ResponseEntity<EpisodeResponse> getEpisodeById(@PathVariable Long episodeId) {
            EpisodeResponse episode = episodeService.findEpisodeById(episodeId);
            return ResponseEntity.ok(episode);
    }

    @DeleteMapping("/{episodeId}/delete")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Long episodeId) {
            episodeService.deleteEpisode(episodeId);
            return ResponseEntity.noContent().build();
    }
}
