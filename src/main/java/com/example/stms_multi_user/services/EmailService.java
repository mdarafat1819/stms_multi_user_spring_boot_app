package com.example.stms_multi_user.services;

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
        message.setSubject("OTP for Nutri-Care School User registration");
        message.setText(
            "Your OTP is: " + otp + "\n\nThis OTP will expire in 5 minutes." + "\n\nDo not share this OTP with anyone."
        );

        mailSender.send(message);
    }
}
