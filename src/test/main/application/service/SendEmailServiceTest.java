package main.application.service;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SendEmailServiceTest {

    @Mock
    JavaMailSender javaMailSenderMock;
    SimpleMailMessage SMMessageMock;


    @Test
    void sendEmails() {

        when(new SimpleMailMessage()).thenReturn(SMMessageMock);
        assertNotNull(SMMessageMock);

    }
}