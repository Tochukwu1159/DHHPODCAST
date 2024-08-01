package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.payloads.requests.CommentDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.CommentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(Long podcastId, CommentDto content);
    void deleteComment(Long commentId);
    List<CommentResponse> getCommentsByPodcast(Long podcastId);

    List<CommentResponse> getAllComments();



}