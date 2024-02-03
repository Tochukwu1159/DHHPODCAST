package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Subscription;
import com.netshiftdigital.dhhpodcast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    List<Subscription> findAll();
    Subscription findByUser(User user);

}
