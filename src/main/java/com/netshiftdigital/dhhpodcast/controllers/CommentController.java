package com.netshiftdigital.dhhpodcast.controllers;
import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.payloads.requests.CommentDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.CommentResponse;
import com.netshiftdigital.dhhpodcast.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/podcasts/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{podcastId}")
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long podcastId,
          @Valid @RequestBody CommentDto content
    ) {
        CommentResponse comment = commentService.addComment( podcastId, content);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{podcastId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPodcast(@PathVariable Long podcastId) {
        List<CommentResponse> comments = commentService.getCommentsByPodcast(podcastId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        List<CommentResponse> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}