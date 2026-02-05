package com.example.stms_multi_user.dto;

import com.example.stms_multi_user.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private Status status;
}
