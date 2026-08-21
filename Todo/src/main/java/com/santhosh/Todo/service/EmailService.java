package com.santhosh.Todo.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.santhosh.Todo.entity.User;

@Service
public class EmailService {

    @Value("${brevo.api-key}")
    private String apiKey;

    @Value("${brevo.from-email}")
    private String fromEmail;

    @Value("${brevo.from-name}")
    private String fromName;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void sendVerificationOtp(User user, String otp) {

        try {

            String name = user.getName() != null
                    ? user.getName()
                    : "User";

            String html = """
                    <html>
                    <body style="font-family: Arial, sans-serif;">

                        <h2>Welcome to Tasks Tracker!</h2>

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

            String json = """
                    {
                        "sender": {
                            "name": "%s",
                            "email": "%s"
                        },
                        "to": [
                            {
                                "email": "%s",
                                "name": "%s"
                            }
                        ],
                        "subject": "Tasks Tracker - Verify Your Account",
                        "htmlContent": "%s"
                    }
                    """.formatted(
                            escapeJson(fromName),
                            escapeJson(fromEmail),
                            escapeJson(user.getEmail()),
                            escapeJson(name),
                            escapeJson(html)
                    );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                    .header("accept", "application/json")
                    .header("api-key", apiKey)
                    .header("content-type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

            if (response.statusCode() >= 200 &&
                response.statusCode() < 300) {

                System.out.println(
                        "Verification email sent successfully: "
                        + response.body()
                );

            } else {

                System.err.println(
                        "Brevo email failed. Status: "
                        + response.statusCode()
                        + " Response: "
                        + response.body()
                );

                throw new RuntimeException(
                        "Unable to send verification email: "
                        + response.body()
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Unable to send verification email: "
                    + e.getMessage(),
                    e
            );
        }
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}