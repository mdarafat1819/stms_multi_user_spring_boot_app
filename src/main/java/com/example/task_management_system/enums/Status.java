package com.example.task_management_system.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("Task is pending"),
    IN_PROGRESS("Task in progress"),
    DONE("Task is completed");

    private String descripiton;

    Status(String descripiton) {
        this.descripiton = descripiton;
    }
}
