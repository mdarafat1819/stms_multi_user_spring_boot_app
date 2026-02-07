package com.example.task_management_system.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("Email \'" +email+ "\' is already registered.");
    }
}
