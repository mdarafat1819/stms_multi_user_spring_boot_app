package com.example.stms_multi_user.services;

import org.springframework.stereotype.Service;

import com.example.stms_multi_user.repositories.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final EmailOtpService emailOtpService;

    public RegistrationService(UserRepository userRepository, EmailOtpService emailOtpService
    ) {
        this.userRepository = userRepository;
        this.emailOtpService = emailOtpService;
    }

}
