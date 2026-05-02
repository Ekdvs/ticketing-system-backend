package com.company.ticket_booking_backend.EmailTemplates;

import com.company.ticket_booking_backend.model.Booking;

import java.time.Year;

public class EmailTemplates {


    // ================= HEADER =================
    public static String header() {
        return """
    <div style="margin:0; padding:0; background-color:#f4f6fb; font-family:Segoe UI, Arial, sans-serif;">

        <!-- Hidden preheader -->
        <div style="display:none; max-height:0; overflow:hidden;">
            Your ticket is confirmed – Thank you for booking with us!
        </div>

        <!-- Top accent bar -->
        <div style="height:5px; background:#6610f2;"></div>

        <!-- Header -->
        <div style="
            background: linear-gradient(135deg, #0d6efd, #6610f2);
            text-align:center;
            padding:50px 20px 40px 20px;
        ">

            <!-- Title -->
            <div style="
                color:#ffffff;
                font-size:26px;
                font-weight:700;
                margin-bottom:10px;
            ">
                Ticket Booking System
            </div>

            <!-- Tagline -->
            <div style="
                display:inline-block;
                color:#ffffff;
                font-size:11px;
                letter-spacing:2px;
                background:rgba(255,255,255,0.15);
                border-radius:20px;
                padding:6px 16px;
            ">
                FAST • SECURE • RELIABLE
            </div>

        </div>

        <!-- Simple separator instead of clip-path -->
        <div style="height:20px; background:#f4f6fb;"></div>

    </div>
    """;
    }

    // ================= FOOTER =================
    public static String footer() {
        return """
    <!-- Wave top for footer (bulges upward into email body) -->
    <div style="
        background-color:#1a1a2e;
        height:32px;
        clip-path:ellipse(55%% 100%% at 50%% 0%%);
        margin-top:-1px;
    "></div>

    <!-- Footer -->
    <div style="
        background-color:#1a1a2e;
        padding:32px 40px 28px 40px;
        text-align:center;
        font-family:'Segoe UI', Arial, sans-serif;
    ">

        <!-- Logo badge -->
        <div style="
            display:inline-block;
            background:linear-gradient(135deg,#0d6efd,#6610f2);
            border-radius:16px;
            padding:11px 20px;
            margin-bottom:14px;
            box-shadow:0 4px 20px rgba(13,110,253,0.4);
        ">
            <img
                src="https://res.cloudinary.com/dnofm4zb9/image/upload/v1777711678/logo_syitom.png"
                alt="Logo"
                width="38"
                height="38"
                style="display:block; border:0; outline:none; text-decoration:none;"
            />
        </div>

        <!-- Brand name -->
        <div style="
            color:#ffffff;
            font-size:20px;
            font-weight:800;
            margin-bottom:4px;
            letter-spacing:0.3px;
        ">Ticket Booking System</div>

        <!-- Tagline -->
        <div style="
            color:rgba(255,255,255,0.35);
            font-size:11px;
            letter-spacing:3px;
            text-transform:uppercase;
            margin-bottom:24px;
        ">Fast &bull; Secure &bull; Reliable</div>

        <!-- Gradient divider -->
        <div style="
            width:80px;
            height:3px;
            background:linear-gradient(90deg,#0d6efd,#6610f2,#d63384);
            margin:0 auto 24px auto;
            border-radius:2px;
        "></div>

        <!-- Links row -->
        <div style="margin-bottom:24px;">
            <a href="https://project-qt6jb.vercel.app/" style="
                display:inline-block;
                color:#7c8bff;
                font-size:12px;
                font-weight:500;
                text-decoration:none;
                margin:4px 5px;
                padding:7px 16px;
                border:1px solid rgba(124,139,255,0.3);
                border-radius:20px;
                background:rgba(124,139,255,0.06);
            ">&#127760; Website</a>
            <a href="https://project-qt6jb.vercel.app/contact" style="
                display:inline-block;
                color:#7c8bff;
                font-size:12px;
                font-weight:500;
                text-decoration:none;
                margin:4px 5px;
                padding:7px 16px;
                border:1px solid rgba(124,139,255,0.3);
                border-radius:20px;
                background:rgba(124,139,255,0.06);
            ">&#128172; Support</a>
            <a href="https://project-qt6jb.vercel.app/privacy" style="
                display:inline-block;
                color:#7c8bff;
                font-size:12px;
                font-weight:500;
                text-decoration:none;
                margin:4px 5px;
                padding:7px 16px;
                border:1px solid rgba(124,139,255,0.3);
                border-radius:20px;
                background:rgba(124,139,255,0.06);
            ">&#128274; Privacy</a>
        </div>

        <!-- Stats row -->
        <div style="
            display:inline-block;
            background:rgba(255,255,255,0.04);
            border:1px solid rgba(255,255,255,0.08);
            border-radius:14px;
            padding:16px 32px;
            margin-bottom:24px;
        ">
            <table style="border-collapse:collapse;">
                <tr>
                    <td style="
                        text-align:center;
                        padding:0 24px 0 0;
                        border-right:1px solid rgba(255,255,255,0.1);
                    ">
                        <div style="color:#ffffff; font-size:17px; font-weight:700;">100%%</div>
                        <div style="color:rgba(255,255,255,0.35); font-size:10px; letter-spacing:1px; text-transform:uppercase; margin-top:3px;">Secure</div>
                    </td>
                    <td style="
                        text-align:center;
                        padding:0 24px;
                        border-right:1px solid rgba(255,255,255,0.1);
                    ">
                        <div style="color:#ffffff; font-size:17px; font-weight:700;">24/7</div>
                        <div style="color:rgba(255,255,255,0.35); font-size:10px; letter-spacing:1px; text-transform:uppercase; margin-top:3px;">Support</div>
                    </td>
                    <td style="text-align:center; padding:0 0 0 24px;">
                        <div style="color:#ffffff; font-size:17px; font-weight:700;">&#128205; LK</div>
                        <div style="color:rgba(255,255,255,0.35); font-size:10px; letter-spacing:1px; text-transform:uppercase; margin-top:3px;">Sri Lanka</div>
                    </td>
                </tr>
            </table>
        </div>

        <!-- Divider line -->
        <div style="border-top:1px solid rgba(255,255,255,0.07); margin:0 0 18px 0;"></div>

        <!-- Copyright -->
        <div style="
            color:rgba(255,255,255,0.25);
            font-size:11px;
            margin-bottom:6px;
        ">&copy; %s Ticket Booking System. All rights reserved.</div>

        <!-- Auto notice -->
        <div style="color:rgba(255,255,255,0.15); font-size:11px;">
            This is an automated email &mdash; please do not reply directly to this message.
        </div>

    </div>

    <!-- Bottom accent bar -->
    <div style="height:5px; background:linear-gradient(90deg,#0d6efd,#6610f2,#d63384);"></div>
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

                <p>Welcome to <b>Ticket Booking System</b>.</p>

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

    public static String ticketEmail(String name, Booking booking) {

        return """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Booking Confirmed</title>
    </head>
    <body style="margin:0; padding:0; font-family:Arial; background:#f4f4f7;">

        <div style="max-width:600px; margin:40px auto; background:#fff; border-radius:10px; overflow:hidden; box-shadow:0 4px 15px rgba(0,0,0,0.1);">

            %s

            <div style="padding:30px 20px; text-align:center; color:#333;">

                <h2 style="color:#28a745;">🎉 Booking Confirmed</h2>

                <p>Hello <b>%s</b>,</p>

                <p>Your ticket has been successfully booked.</p>

                <p><b>Booking ID:</b> %s</p>
                <p><b>Tickets:</b> %d</p>
                <p><b>Total Paid:</b> LKR %.2f</p>

                <a href="%s"
                   style="display:inline-block; margin-top:20px; padding:12px 25px;
                   background:#007bff; color:#fff; text-decoration:none;
                   border-radius:50px; font-weight:bold;">
                   Download Ticket
                </a>

                <p style="margin-top:25px;">
                    Please keep this ticket safe and present it at entry.
                </p>

            </div>

            %s

        </div>

    </body>
    </html>
    """.formatted(
                header(),
                name,
                booking.getBookingId(),
                booking.getQuantity(),
                booking.getTotalPrice(),
                booking.getTicketUrl(), // 🔥 IMPORTANT
                footer()
        );
    }
}