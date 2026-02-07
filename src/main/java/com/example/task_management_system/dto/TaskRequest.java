package com.example.task_management_system.dto;

import com.example.task_management_system.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private Status status;
}
