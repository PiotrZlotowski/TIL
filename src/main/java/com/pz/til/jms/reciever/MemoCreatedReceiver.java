package com.pz.til.jms.reciever;

import com.pz.til.model.Memo;
import com.pz.til.service.email.GenericEmailTemplateResolver;
import com.pz.til.service.email.IEmailService;
import lombok.extern.java.Log;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class MemoCreatedReceiver {

    public static final String TIL_OWNER_EMAIL = "til.owner.email";
    public static final String MEMO_CREATED_SUBJECT = "Memo created!";
    private IEmailService emailService;
    private GenericEmailTemplateResolver<Memo> emailTemplateResolver;
    private Environment environment;


    public MemoCreatedReceiver(IEmailService emailService, GenericEmailTemplateResolver emailTemplateResolver, Environment environment) {
        this.emailService = emailService;
        this.emailTemplateResolver = emailTemplateResolver;
        this.environment = environment;
    }

    @JmsListener(destination = "${jms.channel.memo_created}")
    public void receive(Memo memo) {
        log.info(() -> "Memo recieved using JMS: " + memo);
        String emailContent = getEmailContent(memo);
        sendEmail(emailContent);
    }

    private String getEmailContent(Memo memo) {
        String emailContent = emailTemplateResolver.prepareEmail(memo);
        log.info(() -> "Email content is following: " + emailContent);
        return emailContent;
    }

    private void sendEmail(String emailContent) {
        String toEmail = environment.getProperty(TIL_OWNER_EMAIL);
        emailService.sendEmailMessage(toEmail, MEMO_CREATED_SUBJECT, emailContent);
    }
}
