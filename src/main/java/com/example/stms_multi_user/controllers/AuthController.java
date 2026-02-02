package com.example.stms_multi_user.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_multi_user.dto.OtpRequest;
import com.example.stms_multi_user.services.RegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register/request-otp")
    public ResponseEntity<?> requestOtp (@Valid @RequestBody OtpRequest request) {
        System.out.println("RequestOtp");
        registrationService.requestOtp(request.getEmail());
        return ResponseEntity.ok(
            Map.of("message", "OTP sent to your email")
        );
    }
}
