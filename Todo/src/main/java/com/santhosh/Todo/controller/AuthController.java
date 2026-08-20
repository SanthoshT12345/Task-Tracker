package com.santhosh.Todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santhosh.Todo.dto.LoginRequest;
import com.santhosh.Todo.dto.LoginResponse;
import com.santhosh.Todo.dto.RegisterRequest;
import com.santhosh.Todo.dto.ResendOtpRequest;
import com.santhosh.Todo.dto.VerifyOtpRequest;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.repository.UserRepository;
import com.santhosh.Todo.service.OtpService;
import com.santhosh.Todo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final OtpService otpService;
    private final UserRepository userRepository;

    public AuthController(
            UserService userService,
            OtpService otpService,
            UserRepository userRepository) {

        this.userService = userService;
        this.otpService = otpService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request) {

        return userService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return userService.login(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        otpService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );

        return ResponseEntity.ok(
                "Account activated successfully"
        );
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(
            @RequestBody ResendOtpRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        if (user.isEnabled()) {

            return ResponseEntity.badRequest()
                    .body("Account is already verified");
        }

        otpService.sendOtp(user);

        return ResponseEntity.ok(
                "OTP sent successfully"
        );
    }
}