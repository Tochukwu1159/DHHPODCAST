package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Comment;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPodcastId(Long id);
}
