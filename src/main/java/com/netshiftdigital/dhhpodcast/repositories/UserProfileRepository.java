package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.Profile;
import com.netshiftdigital.dhhpodcast.models.Subscription;
import com.netshiftdigital.dhhpodcast.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<Profile,Long> {
    Profile findByUser(User user);
}