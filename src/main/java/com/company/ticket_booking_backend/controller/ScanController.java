package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.EntryLog;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.BookingRepository;
import com.company.ticket_booking_backend.repository.EntryLogRepository;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.security.QRTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/scan")
@PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')") // Only admins and organizers can scan tickets
public class ScanController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntryLogRepository entryLogRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public ResponseEntity<ApiResponse<String>> scan(@RequestBody Map<String, String> request) {
        String scanneremail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User scanner = userRepository.findByEmail(scanneremail)
                .orElseThrow(() -> new RuntimeException("User not found"));


        try {
            // 🔐 Validate JWT
            String token = request.get("token");
            Claims claims = QRTokenUtil.validateToken(token);

            String bookingId = claims.get("bookingId", String.class);
            String userId = claims.get("userId", String.class);

            // 🔍 Find booking
            Optional<Booking> optionalBooking = bookingRepository.findByBookingId(bookingId);

            if (optionalBooking.isEmpty()) {
                logEntry(bookingId, null, userId, scanner.getId(), "FAILED");
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Invalid Ticket ❌", true, false, null)
                );
            }

            Booking booking = optionalBooking.get();

            // 🔐 Extra security: user check
            if (!booking.getUserId().equals(userId)) {
                logEntry(bookingId, booking.getEventId(), userId, scanner.getId(), "FAILED");
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Invalid Ticket ❌", true, false, null)
                );
            }

            // 🚫 Already used
            if (booking.isTicketUsed()) {
                logEntry(bookingId, booking.getEventId(), userId, scanner.getId(), "FAILED");
                return ResponseEntity.badRequest().body(
                        new ApiResponse<>("Ticket already used ❌", true, false, null)
                );
            }

            // ✅ Mark as used
            booking.setTicketUsed(true);
            booking.setScannedAt(LocalDateTime.now());
            booking.setScannedBy(scanner.getId());
            bookingRepository.save(booking);

            logEntry(bookingId, booking.getEventId(), userId, scanner.getId(), "SUCCESS");

            return ResponseEntity.ok(
                    new ApiResponse<>("Access Granted ✅", false, true, booking.getBookingId())
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Invalid or Expired QR ❌", true, false, null)
            );
        }
    }

    private void logEntry(String bookingId, String eventId, String userId, String scannerId, String status) {
        EntryLog log = EntryLog.builder()
                .bookingId(bookingId)
                .eventId(eventId)
                .userId(userId)
                .scannedBy(scannerId)
                .scannedAt(LocalDateTime.now())
                .status(status)
                .build();

        entryLogRepository.save(log);
    }
}