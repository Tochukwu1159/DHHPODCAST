package com.netshiftdigital.dhhpodcast.service.impl;
import com.netshiftdigital.dhhpodcast.payloads.requests.EmailDetails;
import com.netshiftdigital.dhhpodcast.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sendEmail;

    @Override
    public void sendHtmlEmail(EmailDetails emailDetails) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        try {
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());

            // Process the Thymeleaf template with the provided model
            Context context = new Context();
            context.setVariables(emailDetails.getModel());
            String htmlContent = templateEngine.process(emailDetails.getTemplateName(), context);

            helper.setText(htmlContent, true);
            javaMailSender.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }


}
