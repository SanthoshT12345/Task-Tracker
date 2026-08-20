package com.santhosh.Todo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.entity.User;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationOtp(User user, String otp) {

        try {

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);

            helper.setTo(user.getEmail());

            helper.setSubject(
                    "Progress Tracker - Verify Your Account"
            );

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

            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {

            // Print the actual SMTP/email error in Render logs
            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to send verification email: "
                            + e.getMessage(),
                    e
            );
        }
    }
}