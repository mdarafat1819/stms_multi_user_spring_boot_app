package com.example.task_management_system.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_management_system.dto.AuthResponse;
import com.example.task_management_system.dto.LoginRequest;
import com.example.task_management_system.dto.UserRegistrationRequest;
import com.example.task_management_system.dto.VerifyOtpRequest;
import com.example.task_management_system.services.UserService;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> userRegistration(@RequestBody UserRegistrationRequest request) {
        userService.register(request);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setSuccess(true);
        authResponse.setMessage("An OTP has been sent to your email. Please verify it using the /api/auth/verify-user-email endpoint to activate your account");

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
       AuthResponse authResponse = userService.login(request.getEmail(), request.getPassword());
       return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/verify-user-email")
    public ResponseEntity<AuthResponse> verifyAndActiveUser(@RequestBody VerifyOtpRequest request) {
        userService.verifyEmailAndActivateUser(request);
        return ResponseEntity.ok(
            new AuthResponse(true, "Your OTP has been verified successfully.",null)
        );
    }

}
