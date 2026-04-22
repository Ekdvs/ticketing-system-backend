package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking,String userId);
    List<Booking> getAllBookings();
    Booking getBookingById(String bookingId);

    Booking updatePaymentSuccess(String bookingId, String paymentId);
}
