package com.example.stms_multi_user.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_multi_user.dto.LoginRequest;
import com.example.stms_multi_user.dto.OtpRequest;
import com.example.stms_multi_user.dto.UserRegistrationRequest;
import com.example.stms_multi_user.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody UserRegistrationRequest request) {
        userService.register(request);
        return ResponseEntity.ok(
            Map.of("message", "Registration Successfull, Please verify your email using following url")
        );
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
         authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
        return "Login Successfully";
    }

}
