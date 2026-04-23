package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.Booking;

public interface TicketService {
    void generateFullTicket(Booking booking);
    void revokeTicket(String bookingId);
}
