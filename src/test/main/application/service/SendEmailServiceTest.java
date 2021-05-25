package main.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@ExtendWith(MockitoExtension.class)
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