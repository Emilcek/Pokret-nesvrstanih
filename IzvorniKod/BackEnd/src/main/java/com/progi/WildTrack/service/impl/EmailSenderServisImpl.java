package com.progi.WildTrack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServisImpl {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String firstname, String url) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text = "Pozdrav " + firstname + " stisni link za verfikaciju maila " + url;
        message.setTo(to);
        message.setSubject("WildTrack-Verifikacija");
        message.setText(text);

        javaMailSender.send(message);
        System.out.println("Email sent..") ;
    }
}
