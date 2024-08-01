package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Episodes;
import com.netshiftdigital.dhhpodcast.models.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodesRepository extends JpaRepository<Episodes, Long> {

    List<Episodes> findByPodcast(Podcast podcast);
}
