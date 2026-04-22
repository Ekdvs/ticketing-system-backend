package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.BookingRepository;
import com.company.ticket_booking_backend.repository.EntryLogRepository;
import com.company.ticket_booking_backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private EntryLogRepository entryLogRepository;
    @Autowired
    private EventRepository eventRepository;


    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {

        List<Booking> bookings = bookingRepository.findAll();
        List<EntryLog> logs = entryLogRepository.findAll();

        double totalRevenue = bookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        long totalTickets = bookings.size();

        long usedTickets = bookings.stream()
                .filter(Booking::isTicketUsed)
                .count();

        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", totalRevenue);
        data.put("totalBookings", totalTickets);
        data.put("usedTickets", usedTickets);
        data.put("totalScans", logs.size());

        return ResponseEntity.ok(
                new ApiResponse<>("Admin Stats", false, true, data)
        );
    }

    @GetMapping("/logs")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<List<EntryLog>>> getLogs() {

        List<EntryLog> logs = entryLogRepository.findAll();

        return ResponseEntity.ok(
                new ApiResponse<>("Logs fetched", false, true, logs)
        );
    }
    @GetMapping("/event-chart")
    public ResponseEntity<ApiResponse<List<EventChartDTO>>> getEventChart() {

        List<Booking> bookings = bookingRepository.findAll();

        Map<String, List<Booking>> grouped = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getEventId));

        List<EventChartDTO> result = new ArrayList<>();

        for (String eventId : grouped.keySet()) {

            List<Booking> eventBookings = grouped.get(eventId);

            long totalTickets = eventBookings.size();

            long usedTickets = eventBookings.stream()
                    .filter(Booking::isTicketUsed)
                    .count();

            double revenue = eventBookings.stream()
                    .mapToDouble(Booking::getTotalPrice)
                    .sum();

            // 👉 get event title
            String title = "Event";
            Optional<Event> eventOpt = eventRepository.findById(eventId);
            if (eventOpt.isPresent()) {
                title = eventOpt.get().getTitle();
            }

            result.add(new EventChartDTO(
                    eventId,
                    title,
                    totalTickets,
                    usedTickets,
                    revenue
            ));
        }

        return ResponseEntity.ok(
                new ApiResponse<>("Event chart data", false, true, result)
        );


    }

    @GetMapping("/scan-chart")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getScanChart() {

        List<EntryLog> logs = entryLogRepository.findAll();

        Map<String, Long> chart = logs.stream()
                .filter(log -> "SUCCESS".equals(log.getStatus()))
                .collect(Collectors.groupingBy(
                        log -> log.getScannedAt().toLocalDate().toString(),
                        Collectors.counting()
                ));

        return ResponseEntity.ok(
                new ApiResponse<>("Scan chart", false, true, chart)
        );
    }
}
