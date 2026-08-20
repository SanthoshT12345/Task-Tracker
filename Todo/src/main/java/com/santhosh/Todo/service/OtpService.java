package com.santhosh.Todo.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santhosh.Todo.entity.EmailVerificationOtp;
import com.santhosh.Todo.entity.User;
import com.santhosh.Todo.repository.EmailVerificationOtpRepository;
import com.santhosh.Todo.repository.UserRepository;

@Service
public class OtpService {

    private final EmailVerificationOtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final SecureRandom secureRandom = new SecureRandom();

    public OtpService(
            EmailVerificationOtpRepository otpRepository,
            UserRepository userRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {

        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }


    public void sendOtp(User user) {

        // Remove previous OTP
        otpRepository.deleteByUser(user);

        // Generate 6 digit OTP
        String otp = generateOtp();

        // Hash OTP
        String otpHash =
                passwordEncoder.encode(otp);

        EmailVerificationOtp verificationOtp =
                new EmailVerificationOtp();

        verificationOtp.setUser(user);
        verificationOtp.setOtpHash(otpHash);
        verificationOtp.setExpiresAt(
                LocalDateTime.now().plusMinutes(10)
        );

        otpRepository.save(verificationOtp);

        // Send actual email
        emailService.sendVerificationOtp(
                user,
                otp
        );
    }


    @Transactional
    public void verifyOtp(String email, String otp) {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        if (user.isEnabled()) {

            throw new RuntimeException(
                    "Account is already verified"
            );
        }

        EmailVerificationOtp verificationOtp =
                otpRepository
                        .findTopByUserOrderByCreatedAtDesc(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "OTP not found. Please request a new OTP."
                                )
                        );


        // Check expiry
        if (LocalDateTime.now()
                .isAfter(verificationOtp.getExpiresAt())) {

            otpRepository.delete(verificationOtp);

            throw new RuntimeException(
                    "OTP has expired"
            );
        }


        // Compare entered OTP with hash
        if (!passwordEncoder.matches(
                otp,
                verificationOtp.getOtpHash())) {

            throw new RuntimeException(
                    "Invalid OTP"
            );
        }


        // Activate account
        user.setEnabled(true);

        userRepository.save(user);


        // OTP can no longer be reused
        otpRepository.delete(verificationOtp);
    }


    private String generateOtp() {

        int number =
                100000 + secureRandom.nextInt(900000);

        return String.valueOf(number);
    }
}