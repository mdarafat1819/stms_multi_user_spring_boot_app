package com.example.task_management_system.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Well come to Stms_Multi_User Apps";
    }
 
    @GetMapping("/api/hello") 
    public ResponseEntity<?>hello() {
        return ResponseEntity.ok(
            Map.of("message", "Hello World")
        );
    }
}

