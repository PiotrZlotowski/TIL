package com.pz.til.jms;


import com.pz.til.jms.producer.GenericProducer;
import com.pz.til.jms.reciever.MemoCreatedReceiver;
import com.pz.til.model.Memo;
import com.pz.til.service.email.DefaultSpringEmailService;
import com.pz.til.service.email.IEmailService;
import junit.extension.ApacheMqExtension;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.jms.ConnectionFactory;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@SpringBootTest
@ExtendWith({SpringExtension.class, ApacheMqExtension.class})
@TestPropertySource(locations = "classpath:application.properties")
class JmsMessagesTests {


    @Value("${jms.channel.memo_created}")
    private String memCreatedChannel;

    private GenericProducer genericProducer;
    private IEmailService emailService;

    @Autowired
    JmsMessagesTests(GenericProducer genericProducer, IEmailService emailService) {
        this.genericProducer = genericProducer;
        this.emailService = emailService;
    }

    @Test
    void shouldSentMessageUsingActiveMqBroker() {
        genericProducer.sendMessage(memCreatedChannel, new Memo());
        verify(emailService).sendEmailMessage(anyString(), anyString(), anyString());
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
        public IEmailService emailService() {
            IEmailService mock = mock(DefaultSpringEmailService.class);
            return mock;
        }

        @Bean
        public MemoCreatedReceiver memoCreatedReceiver(IEmailService emailService) {
            return new MemoCreatedReceiver(emailService);
        }

        @Bean
        public JavaMailSender javaMailSender() {
            return new JavaMailSenderImpl();
        }
    }
}
