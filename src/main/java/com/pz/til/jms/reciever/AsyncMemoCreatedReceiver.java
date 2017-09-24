package com.pz.til.jms.reciever;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

@Component
@Log
public class AsyncMemoCreatedReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("Message recieved!");
    }
}
