package com.company.ticket_booking_backend.EmailTemplates;

import java.time.Year;

public class EmailTemplates {

    // ================= HEADER =================
    public static String header() {
        return """
        <div style="
            background: linear-gradient(135deg, #0d6efd, #6610f2);
            color: #fff;
            text-align: center;
            padding: 28px 20px;
            font-size: 26px;
            font-weight: bold;
            letter-spacing: 1px;
        ">
            🎫 Ticket Booking System
            <div style="font-size:14px; font-weight:400; margin-top:5px;">
                Fast • Secure • Reliable Booking Platform
            </div>
        </div>
    """;
    }

    // ================= FOOTER =================
    public static String footer() {
        return """
        <div style="
            background-color: #f8f9fa;
            color: #6c757d;
            text-align: center;
            padding: 20px;
            font-size: 13px;
            border-top: 1px solid #e9ecef;
        ">

            <p style="margin:5px 0;">
                © %s Ticket Booking System. All rights reserved.
            </p>

            <p style="margin:5px 0;">
                📍 Sri Lanka | 🌐 www.ticketbooking.com
            </p>

            <p style="margin:5px 0; font-size:12px;">
                This is an automated email. Please do not reply.
            </p>

        </div>
    """.formatted(Year.now().getValue());
    }

    // ================= WELCOME EMAIL =================
    public static String welcomeEmail(String name, String url) {

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Welcome</title>
        </head>
        <body style="margin:0; padding:0; font-family:Arial; background:#f4f4f7;">

            <div style="max-width:600px; margin:40px auto; background:#fff; border-radius:10px; overflow:hidden; box-shadow:0 4px 15px rgba(0,0,0,0.1);">

                %s

                <div style="padding:30px 20px; text-align:center; color:#333;">
                    <h1 style="color:#007BFF;">Hello, %s!</h1>

                    <p>Welcome to <b>Ticket Booking System</b> — your trusted tech partner.</p>

                    <p>Please verify your email to continue.</p>

                    <a href="%s"
                       style="display:inline-block; margin-top:20px; padding:12px 25px;
                       background:#28a745; color:#fff; text-decoration:none;
                       border-radius:50px; font-weight:bold;">
                       Verify Email
                    </a>

                    <p style="margin-top:25px;">
                        If you need help, we are always here for you.
                    </p>
                </div>

                %s

            </div>

        </body>
        </html>
        """.formatted(header(), name, url, footer());
    }

    // ================= OTP EMAIL =================
    public static String otpEmail(String name, String otp) {

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Email Verification</title>
    </head>
    <body style="margin:0; padding:0; font-family:Arial; background:#f4f4f7;">

        <div style="max-width:600px; margin:40px auto; background:#fff; border-radius:10px; overflow:hidden; box-shadow:0 4px 15px rgba(0,0,0,0.1);">

            %s

            <div style="padding:30px 20px; text-align:center; color:#333;">
                <h2 style="color:#007BFF;">Hello, %s!</h2>

                <p>Welcome to <b>Clinic Management System</b>.</p>

                <p>Use the OTP below to verify your email:</p>

                <div style="
                    font-size:32px;
                    font-weight:bold;
                    letter-spacing:6px;
                    margin:20px 0;
                    color:#28a745;
                ">
                    %s
                </div>

                <p>This OTP is valid for <b>5 minutes</b>.</p>

                <p>If you didn’t request this, please ignore this email.</p>
            </div>

            %s

        </div>

    </body>
    </html>
    """.formatted(header(), name, otp, footer());
    }
}