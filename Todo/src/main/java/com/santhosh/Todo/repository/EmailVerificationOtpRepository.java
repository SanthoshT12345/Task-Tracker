package com.santhosh.Todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santhosh.Todo.entity.EmailVerificationOtp;
import com.santhosh.Todo.entity.User;

public interface EmailVerificationOtpRepository
        extends JpaRepository<EmailVerificationOtp, Long> {

    Optional<EmailVerificationOtp>
    findTopByUserOrderByCreatedAtDesc(User user);

    void deleteByUser(User user);
}