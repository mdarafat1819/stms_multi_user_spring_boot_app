package com.example.task_management_system.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("STMS OTP Service");
        message.setText(
            "Dear User,\nYour OTP is: "+ otp + 
            "\nThis OTP will expire in 5 minutes." + "\nDo not share this OTP with anyone."
        );

        mailSender.send(message);
    }
}
