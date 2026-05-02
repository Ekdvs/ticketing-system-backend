package com.company.ticket_booking_backend.EmailTemplates;

import com.company.ticket_booking_backend.model.Booking;

import java.time.Year;

public class EmailTemplates {

    // ================= HEADER =================
    public static String header() {
        return """
        <div style="margin:0; padding:0; background-color:#f4f6fb; font-family:'Segoe UI', Arial, sans-serif;">

            <!-- Hidden preheader -->
            <div style="display:none; max-height:0; overflow:hidden; color:#f4f6fb;">
                Your ticket is confirmed – Thank you for booking with us!
            </div>

            <!-- Top accent bar -->
            <div style="height:5px; background:linear-gradient(90deg, #0d6efd, #6610f2, #d63384);"></div>

            <!-- Header card -->
            <div style="
                background: linear-gradient(135deg, #0a58ca 0%, #0d6efd 40%, #6610f2 100%);
                padding: 48px 40px 40px 40px;
                text-align: center;
                position: relative;
                overflow: hidden;
            ">

                <!-- Decorative circle top left -->
                <div style="
                    position:absolute; top:-40px; left:-40px;
                    width:140px; height:140px;
                    border-radius:50%;
                    background:rgba(255,255,255,0.06);
                "></div>

                <!-- Decorative circle bottom right -->
                <div style="
                    position:absolute; bottom:-30px; right:-30px;
                    width:100px; height:100px;
                    border-radius:50%;
                    background:rgba(255,255,255,0.06);
                "></div>

                <!-- Decorative circle top right -->
                <div style="
                    position:absolute; top:20px; right:60px;
                    width:50px; height:50px;
                    border-radius:50%;
                    background:rgba(255,255,255,0.04);
                "></div>

                <!-- Icon badge -->
                <div style="
                    display:inline-block;
                    background:rgba(255,255,255,0.15);
                    border: 2px solid rgba(255,255,255,0.3);
                    border-radius:20px;
                    padding:14px 28px;
                    font-size:34px;
                    margin-bottom:20px;
                ">🎫</div>

                <!-- Brand name -->
                <div style="
                    color:#ffffff;
                    font-size:30px;
                    font-weight:800;
                    letter-spacing:0.5px;
                    margin-bottom:10px;
                    text-shadow:0 2px 12px rgba(0,0,0,0.2);
                ">Ticket Booking System</div>

                <!-- Tagline pill -->
                <div style="
                    display:inline-block;
                    color:rgba(255,255,255,0.9);
                    font-size:11px;
                    font-weight:600;
                    letter-spacing:3px;
                    text-transform:uppercase;
                    background:rgba(255,255,255,0.12);
                    border:1px solid rgba(255,255,255,0.25);
                    border-radius:20px;
                    padding:7px 20px;
                ">Fast • Secure • Reliable</div>

            </div>

            <!-- Wave separator -->
            <div style="
                background:linear-gradient(135deg, #0a58ca 0%, #0d6efd 40%, #6610f2 100%);
                height:28px;
                clip-path:ellipse(55% 100% at 50% 0%);
            "></div>

        </div>
    """;
    }

    // ================= FOOTER =================
    public static String footer() {
        return """
        <!-- Wave top for footer -->
        <div style="
            background-color:#1a1a2e;
            height:28px;
            clip-path:ellipse(55%% 100%% at 50%% 100%%);
            margin-top:-1px;
        "></div>

        <!-- Footer -->
        <div style="
            background-color:#1a1a2e;
            padding:36px 40px 32px 40px;
            text-align:center;
            font-family:'Segoe UI', Arial, sans-serif;
        ">
            <!-- Icon badge -->
            <div style="
                display:inline-block;
                background:linear-gradient(135deg,#0d6efd,#6610f2);
                border-radius:16px;
                padding:12px 22px;
                font-size:26px;
                margin-bottom:14px;
                box-shadow:0 4px 20px rgba(13,110,253,0.4);
            ">🎫</div>

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
                margin-bottom:26px;
            ">Fast &bull; Secure &bull; Reliable</div>

            <!-- Gradient divider -->
            <div style="
                width:80px;
                height:3px;
                background:linear-gradient(90deg,#0d6efd,#6610f2,#d63384);
                margin:0 auto 26px auto;
                border-radius:2px;
            "></div>

            <!-- Links row -->
            <div style="margin-bottom:26px;">
                <a href="https://project-qt6jb.vercel.app/" style="
                    display:inline-block;
                    color:#7c8bff;
                    font-size:12px;
                    font-weight:500;
                    text-decoration:none;
                    margin:4px 6px;
                    padding:7px 16px;
                    border:1px solid rgba(124,139,255,0.3);
                    border-radius:20px;
                ">🌐 Website</a>
                <a href="https://project-qt6jb.vercel.app/contact" style="
                    display:inline-block;
                    color:#7c8bff;
                    font-size:12px;
                    font-weight:500;
                    text-decoration:none;
                    margin:4px 6px;
                    padding:7px 16px;
                    border:1px solid rgba(124,139,255,0.3);
                    border-radius:20px;
                ">💬 Support</a>
                <a href="https://project-qt6jb.vercel.app/privacy" style="
                    display:inline-block;
                    color:#7c8bff;
                    font-size:12px;
                    font-weight:500;
                    text-decoration:none;
                    margin:4px 6px;
                    padding:7px 16px;
                    border:1px solid rgba(124,139,255,0.3);
                    border-radius:20px;
                ">🔒 Privacy</a>
            </div>

            <!-- Stats row -->
            <div style="
                display:inline-block;
                background:rgba(255,255,255,0.04);
                border:1px solid rgba(255,255,255,0.08);
                border-radius:14px;
                padding:18px 36px;
                margin-bottom:26px;
            ">
                <table style="border-collapse:collapse;">
                    <tr>
                        <td style="
                            text-align:center;
                            padding:0 28px 0 0;
                            border-right:1px solid rgba(255,255,255,0.1);
                        ">
                            <div style="color:#ffffff; font-size:18px; font-weight:700;">100%%</div>
                            <div style="color:rgba(255,255,255,0.35); font-size:10px; letter-spacing:1px; text-transform:uppercase; margin-top:3px;">Secure</div>
                        </td>
                        <td style="
                            text-align:center;
                            padding:0 28px;
                            border-right:1px solid rgba(255,255,255,0.1);
                        ">
                            <div style="color:#ffffff; font-size:18px; font-weight:700;">24/7</div>
                            <div style="color:rgba(255,255,255,0.35); font-size:10px; letter-spacing:1px; text-transform:uppercase; margin-top:3px;">Support</div>
                        </td>
                        <td style="text-align:center; padding:0 0 0 28px;">
                            <div style="color:#ffffff; font-size:18px; font-weight:700;">📍 LK</div>
                            <div style="color:rgba(255,255,255,0.35); font-size:10px; letter-spacing:1px; text-transform:uppercase; margin-top:3px;">Sri Lanka</div>
                        </td>
                    </tr>
                </table>
            </div>

            <!-- Divider line -->
            <div style="border-top:1px solid rgba(255,255,255,0.07); margin:0 0 20px 0;"></div>

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