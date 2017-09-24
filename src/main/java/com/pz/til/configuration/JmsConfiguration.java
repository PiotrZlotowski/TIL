package com.pz.til.configuration;

import com.pz.til.jms.reciever.AsyncMemoCreatedReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {

    @Bean
    public DefaultMessageListenerContainer defaultMessageListenerContainer(ConnectionFactory connectionFactory, AsyncMemoCreatedReceiver asyncMemoCreatedReceiver) {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        defaultMessageListenerContainer.setMessageListener(asyncMemoCreatedReceiver);
        defaultMessageListenerContainer.setDestinationName("MEMO_CREATED_CHANNEL");
        return defaultMessageListenerContainer;
    }

}
