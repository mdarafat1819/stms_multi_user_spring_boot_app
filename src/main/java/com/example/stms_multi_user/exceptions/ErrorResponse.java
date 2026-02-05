package com.example.stms_multi_user.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int errorCode;
    private String errorMessage;
    private String message;
    private String path;

    public ErrorResponse(int errorCode,String errorMessage, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.message = message;
        this.path = path;
    }
}
