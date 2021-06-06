package main.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class  SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmails(String to,String body,String topic){
        System.out.println("Sending email....");
        SimpleMailMessage mailmessage=new SimpleMailMessage();
        mailmessage.setTo(to);
        mailmessage.setSubject(topic);
        mailmessage.setText(body);
        javaMailSender.send(mailmessage);
        System.out.println("email sent...");
    }

}
