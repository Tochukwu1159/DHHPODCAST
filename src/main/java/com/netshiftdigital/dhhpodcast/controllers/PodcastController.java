package com.netshiftdigital.dhhpodcast.controllers;//package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.payloads.requests.EpisodeRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.PodcastRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.PodcastResponseList;
import com.netshiftdigital.dhhpodcast.service.PodcastService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/podcasts")
@RequiredArgsConstructor
public class PodcastController {


    private final PodcastService podcastService;

//    {
//        "title": "Title of the podcast",
//            "categoryId": 123,
//            "imageFile": "base64-encoded-image-data",
//            "description": "Description of the podcast",
//            "episodes": [
//        {
//            "title": "Title of episode 1",
//                "description": "Description of episode 1",
//                "audioFile": "base64-encoded-audio-data"
//        },
//        {
//            "title": "Title of episode 2",
//                "description": "Description of episode 2",
//                "audioFile": "base64-encoded-audio-data"
//        }
//  ]
//    }
    @PostMapping
    public ResponseEntity<PodcastResponse> createPodcast(
            @RequestParam("title") @Valid String title,
            @RequestParam("categoryId") @Valid Long categoryId,
            @RequestParam("imageFile")  @Valid MultipartFile imageFile,
            @RequestParam("description")  @Valid String description
           ) throws IOException {
        PodcastRequest formData = new PodcastRequest(imageFile,categoryId,title,description);
        PodcastResponse response = podcastService.createPodcast(formData);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PodcastResponse> editPodcast(@PathVariable Long id,
                                                       @RequestParam("title") @Valid String title,
                                                       @RequestParam("categoryId") @Valid Long categoryId,
                                                       @RequestParam("imageFile") @Valid MultipartFile imageFile,
                                                       @RequestParam("description") @Valid String description
    ) throws IOException {
        PodcastRequest formData = new PodcastRequest(imageFile,categoryId,title,description);
        PodcastResponse response = podcastService.editPodcast(id, formData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePodcast(@PathVariable Long id) {
        podcastService.deletePodcast(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodcastResponse> findPodcastById(@PathVariable Long id) {
        PodcastResponse response = podcastService.findPodcastById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<PodcastResponseList> findPodcastByName(@RequestParam String title) {
        PodcastResponseList response = podcastService.findPodcastByName(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<PodcastResponseList> findPodcastByCategoryName(@RequestParam String name) {
        PodcastResponseList response = podcastService.findPodcastByCategoryName(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<PodcastResponse>> findAllPodcasts() {
        List<PodcastResponse> responses = podcastService.findAllPodcasts();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
