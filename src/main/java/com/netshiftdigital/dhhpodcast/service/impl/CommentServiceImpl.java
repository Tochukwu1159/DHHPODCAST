package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.payloads.requests.CommentDto;
import com.netshiftdigital.dhhpodcast.repositories.CommentRepository;
import com.netshiftdigital.dhhpodcast.repositories.PodcastRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PodcastRepository podcastRepository;

    @Override
    public Comment addComment(Long podcastId, CommentDto commentDto) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (user == null) {
            // Handle the case when the user is not found (you may want to throw an exception)
            return null;
        }
        Optional<Podcast> podcast = podcastRepository.findById(podcastId);
        Comment comment = new Comment();
        comment.setUser(user.get());  // Assuming you have a User entity with a constructor that takes userId
        comment.setPodcast(podcast.get());  // Assuming you have a Podcast entity with a constructor that takes podcastId
        comment.setContent(commentDto.getContent());
        comment.setTimestamp(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getCommentsByPodcast(Long podcastId) {
        return commentRepository.findByPodcastId(podcastId);
    }
}
