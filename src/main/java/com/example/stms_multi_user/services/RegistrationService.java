package com.example.stms_multi_user.services;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.example.stms_multi_user.repositories.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final EmailOtpService emailOtpService;

    public RegistrationService(UserRepository userRepository, EmailOtpService emailOtpService) {
        this.userRepository = userRepository;
        this.emailOtpService = emailOtpService;
    }

    public void requestOtp(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        emailOtpService.generateAndSendOtp(email);
    }
}
