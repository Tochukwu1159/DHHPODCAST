package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendHtmlEmail(EmailDetails emailDetails) throws MessagingException;
}
