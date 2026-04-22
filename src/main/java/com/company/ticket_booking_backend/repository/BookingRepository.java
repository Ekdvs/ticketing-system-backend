package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookingRepository extends MongoRepository<Booking, String> {
    Optional<Booking> findByBookingId(String bookingId);
}
