package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.BookingRepository;
import com.company.ticket_booking_backend.repository.EntryLogRepository;
import com.company.ticket_booking_backend.repository.EventRepository;
import com.company.ticket_booking_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizer")
@PreAuthorize("hasRole('ORGANIZER')")
public class OrganizerDashboardController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired private EntryLogRepository entryLogRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private UserRepository userRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {

        String organizeremail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User organizer = userRepository.findByEmail(organizeremail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Event> events = eventRepository.findByOrganizerId(organizer.getId());

        List<String> eventIds = events.stream()
                .map(Event::getId)
                .toList();

        List<Booking> bookings = bookingRepository.findAll()
                .stream()
                .filter(b -> eventIds.contains(b.getEventId()))
                .toList();

        List<EntryLog> logs = entryLogRepository.findAll()
                .stream()
                .filter(l -> eventIds.contains(l.getEventId()))
                .toList();

        double revenue = bookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", revenue);
        data.put("totalBookings", bookings.size());
        data.put("totalScans", logs.size());

        return ResponseEntity.ok(
                new ApiResponse<>("Organizer Stats", false, true, data)
        );
    }

    @GetMapping("/event-chart")
    public ResponseEntity<ApiResponse<List<EventChartDTO>>> getEventChart() {

        String organizerEmail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User organizer = userRepository.findByEmail(organizerEmail)
                .orElseThrow();

        List<Event> events = eventRepository.findByOrganizerId(organizer.getId());

        List<String> eventIds = events.stream()
                .map(Event::getId)
                .toList();

        List<Booking> bookings = bookingRepository.findAll()
                .stream()
                .filter(b -> eventIds.contains(b.getEventId()))
                .toList();

        Map<String, List<Booking>> grouped = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getEventId));

        List<EventChartDTO> result = new ArrayList<>();

        for (Event event : events) {

            List<Booking> eventBookings = grouped.getOrDefault(event.getId(), new ArrayList<>());

            long totalTickets = eventBookings.size();

            long usedTickets = eventBookings.stream()
                    .filter(Booking::isTicketUsed)
                    .count();

            double revenue = eventBookings.stream()
                    .mapToDouble(Booking::getTotalPrice)
                    .sum();

            result.add(new EventChartDTO(
                    event.getId(),
                    event.getTitle(),
                    totalTickets,
                    usedTickets,
                    revenue
            ));
        }

        return ResponseEntity.ok(
                new ApiResponse<>("Organizer event chart", false, true, result)
        );
    }
}
