package com.example.task_management_system.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String email) {
        super("No user found with the email address `" + email + "`");
    }
}