package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.payloads.requests.CommentDto;

import java.util.List;

public interface CommentService {
    Comment addComment(Long podcastId, CommentDto content);
    void deleteComment(Long commentId);
    List<Comment> getCommentsByPodcast(Long podcastId);
}