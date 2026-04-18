package com.company.ticket_booking_backend.service;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
}
