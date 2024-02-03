package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.models.Likes;
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
    public ResponseEntity<Likes> likePodcast( @PathVariable Long podcastId) {
        Likes like = likeService.likePodcast( podcastId);
        return ResponseEntity.ok(like);
    }

    @PostMapping("/unlike/{userId}/{podcastId}")
    public ResponseEntity<Void> unlikePodcast(@PathVariable Long userId, @PathVariable Long podcastId) {
        likeService.unlikePodcast(userId, podcastId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/podcast/{podcastId}")
    public ResponseEntity<List<Likes>> getLikesByPodcast(@PathVariable Long podcastId) {
        List<Likes> likes = likeService.getLikesByPodcast(podcastId);
        return ResponseEntity.ok(likes);
    }
}
