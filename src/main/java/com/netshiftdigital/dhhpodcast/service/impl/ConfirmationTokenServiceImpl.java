package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.models.ConfirmationToken;
import com.netshiftdigital.dhhpodcast.repositories.ConfirmationTokenRepository;
import com.netshiftdigital.dhhpodcast.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Override
    public void saveConfirmationToken(ConfirmationToken token) {

        confirmationTokenRepository.saveAndFlush(token);
    }

    @Override
    public ConfirmationToken getToken(String token) {

        return confirmationTokenRepository.findByToken(token);
    }
    @Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

}