package com.netshiftdigital.dhhpodcast.service;


import com.netshiftdigital.dhhpodcast.models.ConfirmationToken;

public interface ConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    ConfirmationToken getToken(String token);
    int setConfirmedAt(String token);
}
