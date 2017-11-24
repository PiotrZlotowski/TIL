package com.pz.til.service.email;

import lombok.extern.java.Log;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Log
public class DefaultSpringEmailService implements IEmailService {

    private JavaMailSender javaMailSender;

    public DefaultSpringEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailMessage(String to, String subject, String emailContent) {

        if (hasInvalidEmailDetails(to, subject, emailContent)) {
            throw new IllegalArgumentException("Please provide correct e-mail details.");
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.info(() -> "Exception caught" + e);
        }
    }

    private boolean hasInvalidEmailDetails(String to, String subject, String emailContent) {
        boolean hasNullEmailParameters = (to == null || subject == null || emailContent == null);
        if (hasNullEmailParameters) {
            return true;
        }
        return (to.isEmpty() || subject.isEmpty() || emailContent.isEmpty());
    }
}
