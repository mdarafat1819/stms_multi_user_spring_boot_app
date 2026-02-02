package com.example.stms_multi_user.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_multi_user.dto.OtpRequest;
import com.example.stms_multi_user.dto.VerifyOtpRequest;
import com.example.stms_multi_user.services.EmailOtpService;

import jakarta.validation.Valid;

@RestController
public class HomeController {
    private final EmailOtpService emailOtpService;

    public HomeController(EmailOtpService emailOtpService) {
        this.emailOtpService = emailOtpService;
    }

    @GetMapping("/")
    public String home() {
        return "Well come to Stms_Multi_User Apps";
    }
    @PostMapping("/api/request-otp")
    public ResponseEntity<?> requestOtp (@Valid @RequestBody OtpRequest request) {
        System.out.println("RequestOtp");
        emailOtpService.generateAndSendOtp(request.getEmail());
        return ResponseEntity.ok(
            Map.of("message", "OTP sent to your email")
        );
    }
    @PostMapping("/api/verify-otp")
    public ResponseEntity<?>verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
         boolean status = emailOtpService.validateOtp(request.getEmail(), request.getOtp());
         if(status) {
            return ResponseEntity.ok(
            Map.of("message", "Successfully Veryfied")
        );}

        return ResponseEntity.ok(
            Map.of("message", "Verification Failed"));
    }

}
