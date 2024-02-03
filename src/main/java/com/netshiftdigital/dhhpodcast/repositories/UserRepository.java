
package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);

    User findByEmailAndRoles(String email, Roles role);
}
