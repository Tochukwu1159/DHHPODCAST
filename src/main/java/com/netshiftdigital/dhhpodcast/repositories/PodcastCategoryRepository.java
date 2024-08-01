package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.PodcastCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastCategoryRepository extends JpaRepository<PodcastCategory, Long> {

    PodcastCategory findByNameIgnoreCase(String name);
}
