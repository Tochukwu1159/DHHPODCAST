package com.netshiftdigital.dhhpodcast.Security;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        CustomUserDetails userDetails = null;
        if (user != null) {
            userDetails = new CustomUserDetails(user);
        }
        return userDetails;
    }
}
