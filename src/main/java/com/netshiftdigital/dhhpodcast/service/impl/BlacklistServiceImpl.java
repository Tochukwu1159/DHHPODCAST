package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.models.BlacklistedToken;
import com.netshiftdigital.dhhpodcast.repositories.BlacklistedTokenRepository;
import com.netshiftdigital.dhhpodcast.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public BlacklistedToken blacklistToken(String token) {
        BlacklistedToken blackListedToken = new BlacklistedToken();
        blackListedToken.setToken(token.substring(7));

        blacklistedTokenRepository.save(blackListedToken);

        return blackListedToken;
    }

    public boolean isTokenBlacklisted(String token) {
        // Check if the token exists in the database
        return blacklistedTokenRepository.existsByToken(token);
    }
}
