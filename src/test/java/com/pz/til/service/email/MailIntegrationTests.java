package com.pz.til.service.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class MailIntegrationTests {

    private GreenMail greenMail;
    private IEmailService emailService;
    @Autowired
    MailIntegrationTests(GreenMail greenMail, IEmailService emailService) {
        this.greenMail = greenMail;
        this.emailService = emailService;
    }

    @BeforeEach
    void setUp() {
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void shouldReceiveStandardMailWhenSentWithEmailService() throws MessagingException, IOException {
        // when
        emailService.sendEmailMessage("test@localhost.com", "Test message", "Test message");
        // then
        Message[] messages = greenMail.getReceivedMessages();
        assertThat(messages).hasSize(1);
        assertThat(messages[0].getSubject()).isEqualToIgnoringCase("Test message");
    }

    @Test
    void shouldThrowExceptionWhenEmptyEmailDetailsProvided() throws MessagingException, IOException {
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailService.sendEmailMessage("", "", ""));
        // then
        assertThat(exception).hasMessage("Please provide correct e-mail details.");
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowExceptionWhenNullEmailDetailsProvided() throws MessagingException, IOException {
        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> emailService.sendEmailMessage(null, null, null));
        // then
        assertThat(exception).hasMessage("Please provide correct e-mail details.");
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }




    @TestConfiguration
    static class MockMailServerConfiguration {

        @Bean
        public GreenMail greenMail() {
            return new GreenMail(ServerSetupTest.SMTP);
        }
    }
}
