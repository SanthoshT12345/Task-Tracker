package com.santhosh.Todo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import com.santhosh.Todo.entity.User;

@Service
public class EmailService {

    private final Resend resend;

    @Value("${resend.from-email}")
    private String fromEmail;

    public EmailService(
            @Value("${resend.api-key}") String apiKey) {

        this.resend = new Resend(apiKey);
    }

    public void sendVerificationOtp(User user, String otp) {

        try {

            String name = user.getName() != null
                    ? user.getName()
                    : "User";

            String html = """
                    <html>
                    <body style="font-family: Arial, sans-serif;">

                        <h2>Welcome to Progress Tracker!</h2>

                        <p>Hello %s,</p>

                        <p>
                            Thank you for creating your account.
                            Please use the OTP below to verify your email.
                        </p>

                        <div style="
                            font-size: 32px;
                            font-weight: bold;
                            letter-spacing: 8px;
                            padding: 20px;
                            background: #f1f5f9;
                            width: fit-content;
                            border-radius: 10px;
                        ">
                            %s
                        </div>

                        <p>
                            This OTP will expire in
                            <strong>10 minutes</strong>.
                        </p>

                        <p>
                            If you did not create this account,
                            you can safely ignore this email.
                        </p>

                        <p>
                            Regards,<br>
                            Progress Tracker Team
                        </p>

                    </body>
                    </html>
                    """.formatted(name, otp);

            CreateEmailOptions params =
                    CreateEmailOptions.builder()
                            .from(fromEmail)
                            .to(user.getEmail())
                            .subject("Progress Tracker - Verify Your Account")
                            .html(html)
                            .build();

            CreateEmailResponse response =
                    resend.emails().send(params);

            System.out.println(
                    "Verification email sent. ID: "
                    + response.getId()
            );

        } catch (ResendException e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to send verification email: "
                    + e.getMessage(),
                    e
            );
        }
    }
}