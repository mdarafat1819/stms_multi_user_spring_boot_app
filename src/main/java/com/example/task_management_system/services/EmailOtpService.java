package com.example.task_management_system.services;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.task_management_system.entities.EmailOtp;
import com.example.task_management_system.repositories.EmailOtpRepository;

import jakarta.transaction.Transactional;

@Service
public class EmailOtpService {
    private final EmailOtpRepository emailOtpRepository;
    private final EmailService emailService;

    public EmailOtpService(EmailOtpRepository emailOtpRepository, EmailService emailService) {
        this.emailOtpRepository = emailOtpRepository;
        this.emailService = emailService;
    }

    private String generateOtp() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }

    @Transactional
    public void generateAndSendOtp(String email) {
        emailOtpRepository.deleteByEmail(email);

        EmailOtp  emailOtp = new EmailOtp();
        emailOtp.setEmail(email);
        emailOtp.setOtp(generateOtp());
        emailOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        emailOtpRepository.save(emailOtp);

        emailService.sendOtpEmail(email, emailOtp.getOtp());
       // return emailOtp.getOtp();

    }

     public boolean validateOtp(String email, String otp) {

        return emailOtpRepository.findByEmail(email)
                .filter(storedOtp ->
                        storedOtp.getOtp().equals(otp) &&
                        storedOtp.getExpiresAt().isAfter(LocalDateTime.now())
                )
                .isPresent();
    }

    public void deleteOtp(String email) {
        emailOtpRepository.deleteByEmail(email);
    }

}
