package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Podcast;
import com.netshiftdigital.dhhpodcast.models.PodcastCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
    List<Podcast> findByIsFavoriteTrue();
    List<Podcast> findAllByCategory(PodcastCategory podcastCategory);
    List<Podcast> findAllByTitleContainingIgnoreCase(String title);
    List<Podcast> findAllByCategoryName(String name);

//    @Query("SELECT DISTINCT p FROM Podcast p " +
//            "LEFT JOIN SubscriptionPlans sp ON p.subscriptionPlans.id = sp.id " +
//            "LEFT JOIN User u ON u.subscriptionPlan.id = sp.id " +
//            "WHERE u.id = :userId OR p.subscriptionPlans IS NULL")
//    List<Podcast> findPodcastsByUserIdWithSubscriptionPlan(@Param("userId") Long userId);
//
//
//    @Query("SELECT DISTINCT p FROM Podcast p " +
//            "JOIN p.subscriptionPlans sp " +
//            "LEFT JOIN User u ON u.subscriptionPlan.id = sp.id " +
//            "WHERE (u.id = :userId OR p NOT IN " +
//            "(SELECT p2 FROM Podcast p2 " +
//            "JOIN p2.subscriptionPlans sp2 " +
//            "JOIN User u2 ON u2.subscriptionPlan.id = sp2.id " +
//            "WHERE u2.id = :userId))")
//    List<Podcast> findPodcastsByUserIdWithSubscriptionPlans(@Param("userId") Long userId);



}
