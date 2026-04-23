package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.Booking;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
    void sendTicket(String toEmail,String name,Booking booking, byte[] pdf);
}
