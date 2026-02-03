package com.example.stms_multi_user.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.stms_multi_user.dto.UserRegistrationRequest;
import com.example.stms_multi_user.dto.VerifyOtpRequest;
import com.example.stms_multi_user.entities.EmailOtp;
import com.example.stms_multi_user.entities.User;
import com.example.stms_multi_user.repositories.EmailOtpRepository;
import com.example.stms_multi_user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailOtpService emailOtpService;

    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EmailOtpService emailOtpService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailOtpService = emailOtpService;
    }

    public void register(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exist with this email");
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("PENDING");

        userRepository.save(user);

        emailOtpService.generateAndSendOtp(request.getEmail());

    }
    @Transactional
    public void verifyEmailAndActiveUser(VerifyOtpRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            boolean status = false;
            status = emailOtpService.validateOtp(request.getEmail(), request.getOtp());

            if (status) {
                user.setRole("USER");
                userRepository.save(user);
                emailOtpService.deleteOtp(request.getEmail());
            } else {
                throw new RuntimeException("Invalid OTP for email: " + request.getEmail());
            }

        } else
            throw new RuntimeException("User not found with email: " + request.getEmail());
    }

}
