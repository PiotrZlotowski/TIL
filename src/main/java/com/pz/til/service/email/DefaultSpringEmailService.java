package com.pz.til.service.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DefaultSpringEmailService implements IEmailService {

    private JavaMailSender javaMailSender;

    public DefaultSpringEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmailMessage(String to, String subject, String emailContent) {
    }
}
