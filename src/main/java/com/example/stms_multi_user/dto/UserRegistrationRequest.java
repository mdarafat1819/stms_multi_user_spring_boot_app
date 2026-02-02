package com.example.stms_multi_user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegistrationRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String firstName;
    
    private String lastName;

    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

}
