package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {

}
