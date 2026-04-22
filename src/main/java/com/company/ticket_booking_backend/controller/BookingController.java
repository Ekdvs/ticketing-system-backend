package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;



    // ================= CREATE BOOKING =================
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBooking(@RequestBody Booking booking) {

        String userId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Booking newBooking = bookingService.createBooking(booking,userId);


        return ResponseEntity.ok(new ApiResponse("Booking created successfully", false, true, newBooking));
    }

    // ================= GET BY BOOKING ID =================
    @GetMapping("/getBookingById")
    public ResponseEntity<ApiResponse> getBookingById(@RequestParam String bookingId) {
        if (bookingId == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Booking ID is required", false, false, null));
        }

        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            return ResponseEntity.badRequest().body(new ApiResponse("Booking not found", false, false, null));

        }
        return ResponseEntity.ok(new ApiResponse("Booking found", true, true, booking));
    }

    // ================= GET ALL BOOKING =================
    @GetMapping("/getAllBooking")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse> getAllBooking() {
        List<Booking> bookingList = bookingService.getAllBookings();

        return ResponseEntity.ok(new ApiResponse("All bookings found", true, true, bookingList));
    }
}
