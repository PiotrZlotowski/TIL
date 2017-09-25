package com.pz.til.service.email;

public interface IEmailService {
    void sendEmailMessage(String to, String subject, String emailContent);
}
