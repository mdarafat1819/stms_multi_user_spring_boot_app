package com.example.stms_multi_user.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Integer id) {
        super("Task not found with id: " + id);
    }
}
