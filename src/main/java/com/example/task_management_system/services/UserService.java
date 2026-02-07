package com.example.task_management_system.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.example.task_management_system.dto.AuthResponse;
import com.example.task_management_system.dto.UserRegistrationRequest;
import com.example.task_management_system.dto.VerifyOtpRequest;
import com.example.task_management_system.entities.TempUser;
import com.example.task_management_system.entities.User;
import com.example.task_management_system.exceptions.UserAlreadyExistsException;
import com.example.task_management_system.exceptions.UserNotFoundException;
import com.example.task_management_system.repositories.TempUserRepository;
import com.example.task_management_system.repositories.UserRepository;
import com.example.task_management_system.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TempUserRepository tempUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailOtpService emailOtpService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    public UserService(
        TempUserRepository tempUserRepository,
        UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailOtpService emailOtpService,
            JwtUtil jwtUtil,
            ModelMapper modelMapper
        ) {
        this.tempUserRepository = tempUserRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailOtpService = emailOtpService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    public void register(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        TempUser tempUser = modelMapper.map(request, TempUser.class);
        tempUser.setPassword(passwordEncoder.encode(request.getPassword()));

        tempUserRepository.save(tempUser);
        emailOtpService.generateAndSendOtp(request.getEmail());
    }

    public AuthResponse login(String email, String password) {

        if(userRepository.findByEmail(email).isEmpty()) {
            throw new UserNotFoundException(email);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtUtil.generateToken(email);
        return new AuthResponse(true, "Logged in successfully.", token);
        
    }

    @Transactional
    public void verifyEmailAndActivateUser(VerifyOtpRequest request) {

        TempUser tempUser = tempUserRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RuntimeException("User not found with email: " + request.getEmail()));

        boolean status = false;
        status = emailOtpService.validateOtp(request.getEmail(), request.getOtp());

        if (status) {
            User user = modelMapper.map(tempUser, User.class);

            userRepository.save(user);
            emailOtpService.deleteOtp(request.getEmail());
            tempUserRepository.delete(tempUser);
        } else {
            throw new RuntimeException("Invalid OTP for email `" + request.getEmail() + "`");
        }
    }

}
