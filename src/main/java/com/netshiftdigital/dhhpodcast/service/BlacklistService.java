package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.models.BlacklistedToken;

public interface BlacklistService {
    BlacklistedToken blacklistToken(String token);
//    boolean tokenExist(String token);
    boolean isTokenBlacklisted(String token);
}
