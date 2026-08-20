package com.santhosh.Todo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.dto.LoginRequest;
import com.santhosh.Todo.dto.LoginResponse;
import com.santhosh.Todo.dto.RegisterRequest;
import com.santhosh.Todo.dto.UserResponse;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.exception.EmailAlreadyExistsException;
import com.santhosh.Todo.exception.PasswordMismatchException;
import com.santhosh.Todo.exception.UserNotFoundException;
import com.santhosh.Todo.repository.UserRepository;
import com.santhosh.Todo.security.JwtService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OtpService otpService;

    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            OtpService otpService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Email already exists"
            );
        }

        if (!request.getPassword()
                .equals(request.getConfirmPassword())) {

            throw new PasswordMismatchException(
                    "Password and Confirm Password do not match"
            );
        }

        String encryptedPassword =
                passwordEncoder.encode(request.getPassword());

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encryptedPassword);

        // Account is NOT verified yet
        user.setEnabled(false);

        User savedUser =
                userRepository.save(user);

        // Generate OTP and send email
        otpService.sendOtp(savedUser);

        return "Registration successful. Verification OTP sent to your email.";
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );

        // Don't allow unverified users to login
        if (!user.isEnabled()) {

            throw new RuntimeException(
                    "Please verify your email before logging in"
            );
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token =
                jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                "Login Successful"
        );
    }

    @Override
    public UserResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}