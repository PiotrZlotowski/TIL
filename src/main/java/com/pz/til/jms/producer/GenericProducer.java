package com.pz.til.jms.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class GenericProducer {

    private JmsTemplate jmsTemplate;

    public GenericProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String destination, Object message) {
        jmsTemplate.convertAndSend(destination, message);
    }

}
