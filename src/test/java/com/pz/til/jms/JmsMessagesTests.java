package com.pz.til.jms;


import com.pz.til.jms.producer.GenericProducer;
import com.pz.til.jms.reciever.MemoCreatedReceiver;
import com.pz.til.model.Memo;
import junit.extension.ApacheMqExtension;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.jms.ConnectionFactory;
import java.util.Arrays;


@ExtendWith({ApacheMqExtension.class, SpringExtension.class})
class JmsMessagesTests {


    @Value("${jms.channel.memo_created}")
    private String memCreatedChannel;

    private GenericProducer genericProducer;
    private MemoCreatedReceiver memoCreatedReceiver;

    @Autowired
    JmsMessagesTests(GenericProducer genericProducer, MemoCreatedReceiver memoCreatedReceiver) {
        this.genericProducer = genericProducer;
        this.memoCreatedReceiver = memoCreatedReceiver;
    }

    @Test
    void shouldSentMessageUsingActiveMqBroker() {
        genericProducer.sendMessage(memCreatedChannel, new Memo());

    }


    @TestConfiguration
    static class JmsBeanConfiguration {

        @Bean
        public ActiveMQConnectionFactory activeMQConnectionFactory() {
            ActiveMQConnectionFactory  activeMQConnectionFactory = new ActiveMQConnectionFactory();
            activeMQConnectionFactory.setBrokerURL("vm://localhost?create=false");
            activeMQConnectionFactory.setTrustedPackages(Arrays.asList("com.pz.til.model"));
            return activeMQConnectionFactory;
        }


        @Bean
        public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
            return new JmsTemplate(connectionFactory);
        }

        @Bean
        public GenericProducer genericProducer(JmsTemplate jmsTemplate) {
            return new GenericProducer(jmsTemplate);
        }

        @Bean
        public MemoCreatedReceiver memoCreatedReceiver() {
            return new MemoCreatedReceiver();
        }
    }
}
