package com.example.stms_multi_user.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stms_multi_user.services.EmailOtpService;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Well come to Stms_Multi_User Apps";
    }
}
