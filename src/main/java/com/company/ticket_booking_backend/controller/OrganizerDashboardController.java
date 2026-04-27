package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.*;
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
    @Autowired
    private OrganizerEarningRepository earningRepository;

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStats() {

        String organizeremail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName(); // ✅ FIXED (better than getPrincipal)

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

        // ✅ ADD THIS (MISSING PART)
        List<OrganizerEarning> earnings = earningRepository.findByOrganizerId(organizer.getId());

        // ✅ CORRECT revenue (after platform fee)
        double revenue = earnings.stream()
                .mapToDouble(OrganizerEarning::getOrganizerAmount)
                .sum();

        Map<String, Object> data = new HashMap<>();
        data.put("totalRevenue", revenue);
        data.put("totalBookings", bookings.size());
        data.put("totalScans", logs.size());
        data.put("totalEarningsRecords", earnings.size()); // optional

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

    @GetMapping("/earnings")
    public ResponseEntity<ApiResponse<List<OrganizerEarning>>> getEarnings() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrganizerEarning> earnings = earningRepository.findByOrganizerId(organizer.getId());

        return ResponseEntity.ok(
                new ApiResponse<>("Organizer earnings", false, true, earnings)
        );
    }

    @GetMapping("/daily-revenue")
    public ResponseEntity<ApiResponse<List<RevenueChartDTO>>> getDailyRevenue() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User organizer = userRepository.findByEmail(email)
                .orElseThrow();

        List<OrganizerEarning> earnings =
                earningRepository.findByOrganizerId(organizer.getId());

        Map<String, Double> grouped = earnings.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreatedAt().toLocalDate().toString(),
                        Collectors.summingDouble(OrganizerEarning::getOrganizerAmount)
                ));

        List<RevenueChartDTO> result = grouped.entrySet()
                .stream()
                .map(e -> new RevenueChartDTO(e.getKey(), e.getValue()))
                .sorted((a, b) -> a.getLabel().compareTo(b.getLabel()))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Daily revenue", false, true, result)
        );
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<RevenueChartDTO>>> getMonthlyRevenue() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User organizer = userRepository.findByEmail(email)
                .orElseThrow();

        List<OrganizerEarning> earnings =
                earningRepository.findByOrganizerId(organizer.getId());

        Map<String, Double> grouped = earnings.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCreatedAt().getYear() + "-" + e.getCreatedAt().getMonthValue(),
                        Collectors.summingDouble(OrganizerEarning::getOrganizerAmount)
                ));

        List<RevenueChartDTO> result = grouped.entrySet()
                .stream()
                .map(e -> new RevenueChartDTO(e.getKey(), e.getValue()))
                .sorted((a, b) -> a.getLabel().compareTo(b.getLabel()))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Monthly revenue", false, true, result)
        );
    }

    @GetMapping("/earning-summary")
    public ResponseEntity<ApiResponse<Map<String, Double>>> getSummary() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User organizer = userRepository.findByEmail(email)
                .orElseThrow();

        List<OrganizerEarning> earnings =
                earningRepository.findByOrganizerId(organizer.getId());

        double paid = earnings.stream()
                .filter(OrganizerEarning::isPaid)
                .mapToDouble(OrganizerEarning::getOrganizerAmount)
                .sum();

        double pending = earnings.stream()
                .filter(e -> !e.isPaid())
                .mapToDouble(OrganizerEarning::getOrganizerAmount)
                .sum();

        Map<String, Double> data = new HashMap<>();
        data.put("paid", paid);
        data.put("pending", pending);

        return ResponseEntity.ok(
                new ApiResponse<>("Earning summary", false, true, data)
        );
    }




}
