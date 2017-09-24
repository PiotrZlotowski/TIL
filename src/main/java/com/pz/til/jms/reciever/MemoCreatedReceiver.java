package com.pz.til.jms.reciever;

import com.pz.til.model.Memo;
import lombok.extern.java.Log;
import org.springframework.jms.annotation.JmsListener;

//@Component
@Log
public class MemoCreatedReceiver {

    @JmsListener(destination = "${jms.channel.memo_created}")
    public void receive(Memo memo) {
        log.info(() -> "Memo recieved using JMS: " + memo);
    }


}
