package com.hunt.service.impl;

import com.hunt.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override

    /**
     * @param to
     * @param email
     * @param from
     * @param subject
     */
    @Async
    public void send(String to, String email, String from, String subject) {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
            mailSender.send(mimeMessage);
    }

    /**
     * @param emailTemplate
     * @param strings
     * @return
     */
    @Override
    public String emailBuilder(String emailTemplate, String... strings) {
            String emailContent = String.format(emailTemplate, strings);
            return emailContent;
    }
}
