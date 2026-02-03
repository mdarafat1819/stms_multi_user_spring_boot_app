package com.example.stms_multi_user.controllers;

import java.util.Map;

import org.apache.catalina.valves.rewrite.RewriteCond;
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
import com.example.stms_multi_user.dto.VerifyOtpRequest;
import com.example.stms_multi_user.security.JwtUtil;
import com.example.stms_multi_user.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegistration(@RequestBody UserRegistrationRequest request) {
        userService.register(request);
        return ResponseEntity.ok(
                Map.of("message",
                        "An OTP has been sent to your email. Please verify it using the /api/auth/verify-user-email endpoint to activate your account."));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        String token = jwtUtil.generateToken(request.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "token", token));
    }

    @PostMapping("/verify-user-email")
    public ResponseEntity<?> verifyAndActiveUser(@RequestBody VerifyOtpRequest request) {
        System.out.println(request.getEmail() + request.getOtp());
        userService.verifyEmailAndActiveUser(request);
        return ResponseEntity.ok(
            Map.of(
                "message", "Successfully veryfied"
            )
        );
    }

}
