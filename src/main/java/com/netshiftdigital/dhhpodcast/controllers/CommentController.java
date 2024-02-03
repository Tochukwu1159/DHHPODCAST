package com.netshiftdigital.dhhpodcast.controllers;
import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.payloads.requests.CommentDto;
import com.netshiftdigital.dhhpodcast.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/add/{podcastId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long podcastId,
            @RequestBody CommentDto content
    ) {
        Comment comment = commentService.addComment( podcastId, content);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/podcast/{podcastId}")
    public ResponseEntity<List<Comment>> getCommentsByPodcast(@PathVariable Long podcastId) {
        List<Comment> comments = commentService.getCommentsByPodcast(podcastId);
        return ResponseEntity.ok(comments);
    }
}