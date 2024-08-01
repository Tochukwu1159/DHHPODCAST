package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.models.Likes;
import com.netshiftdigital.dhhpodcast.payloads.responses.LikeResponse;
import com.netshiftdigital.dhhpodcast.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{podcastId}")
    public ResponseEntity<String> toggleLike(@PathVariable Long podcastId) {
        String like = likeService.togglePodcastLike( podcastId);
        return ResponseEntity.ok(like);
    }


    @GetMapping("/favorite")
    public ResponseEntity< LikeResponse> allFavoritePodcasts() {
        LikeResponse likes = likeService.allFavoritePodcasts();
        return ResponseEntity.ok(likes);
    }
}
