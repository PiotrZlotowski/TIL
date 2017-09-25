package com.pz.til.jms.reciever;

import com.pz.til.model.Memo;
import com.pz.til.service.email.IEmailService;
import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class MemoCreatedReceiver {

    private IEmailService emailService;

    public MemoCreatedReceiver(IEmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "${jms.channel.memo_created}")
    public void receive(Memo memo) {
        log.info(() -> "Memo recieved using JMS: " + memo);
        // TODO: To implement proper e-mail handling
        emailService.sendEmailMessage("", "", "");

    }


}
