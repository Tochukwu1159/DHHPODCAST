package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.Profile;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.payloads.requests.CommentDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.CommentResponse;
import com.netshiftdigital.dhhpodcast.repositories.CommentRepository;
import com.netshiftdigital.dhhpodcast.repositories.PodcastRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserProfileRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentResponse addComment(Long podcastId, CommentDto commentDto) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found");
        }

        Profile profile = userProfileRepository.findByUser(user.get());
        Optional<Podcast> podcastOptional = podcastRepository.findById(podcastId);
        if (!podcastOptional.isPresent()) {
            throw new ResourceNotFoundException("Podcast not found");
        }

        Podcast podcast = podcastOptional.get();

        Comment comment = new Comment();
        comment.setProfile(profile);
        comment.setPodcast(podcast);
        comment.setContent(commentDto.getContent());
        comment = commentRepository.save(comment);

        String profileName = profile.getUser().getFirstName() + " " + profile.getUser().getLastName(); // Assuming you have a method to get the full name
        return new CommentResponse(podcastId,comment.getId(),profile.getId(), profileName, commentDto.getContent());
    }
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentResponse> getCommentsByPodcast(Long podcastId) {
        return commentRepository.findByPodcastId(podcastId).stream().map((element) -> modelMapper.map(element, CommentResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll().stream().map(comment -> {
            Profile profile = comment.getProfile();
            User user = profile.getUser();
            String profileName = user.getFirstName() + " " + user.getLastName();
            return new CommentResponse(
                    comment.getPodcast().getId(),
                    comment.getId(),
                    profile.getId(),
                    profileName,
                    comment.getContent()
            );
        }).collect(Collectors.toList());
    }
}
