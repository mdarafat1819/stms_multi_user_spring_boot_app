package com.example.stms_multi_user.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.stms_multi_user.dto.UserRegistrationRequest;
import com.example.stms_multi_user.entities.User;
import com.example.stms_multi_user.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegistrationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("User already exist with this email");
        }

        User user = new User();

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("PENDING");

        userRepository.save(user);
    }
    

}
