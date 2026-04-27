package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.*;
import com.company.ticket_booking_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private OrganizerEarningRepository earningRepository;
    @Autowired
    private WithdrawalRepository withdrawalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;


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


    @PatchMapping("/pay/{id}")
    public ResponseEntity<ApiResponse> markAsPaid(@PathVariable String id) {

        OrganizerEarning earning = earningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        earning.setPaid(true);
        earningRepository.save(earning);

        return ResponseEntity.ok(
                new ApiResponse("Marked as paid", false, true, null)
        );
    }
    @PatchMapping("/approve-withdraw/{id}")
    public ResponseEntity<ApiResponse> approve(@PathVariable String id) {

        WithdrawalRequest w = withdrawalRepository.findById(id).orElseThrow();
        w.setStatus("APPROVED");
        withdrawalRepository.save(w);

        // 🔔 Notify the organizer their withdrawal was approved
        notificationService.sendToOrganizer(
                w.getOrganizerId(),           // make sure WithdrawalRequest has organizerId field
                "WITHDRAW_APPROVED",
                "Withdrawal Approved ✅",
                "Your withdrawal request of Rs " + w.getAmount() + " has been approved!"
        );

        return ResponseEntity.ok(new ApiResponse("Approved", false, true, null));
    }


    @GetMapping("/withdrawals")
    public ResponseEntity<ApiResponse<List<WithdrawalRequest>>> getWithdrawals() {

        List<WithdrawalRequest> list = withdrawalRepository.findAll();

        return ResponseEntity.ok(
                new ApiResponse<>("Withdrawals fetched", false, true, list)
        );
    }

    @GetMapping("/daily-revenue")
    public ResponseEntity<ApiResponse<List<RevenueChartDTO>>> getDailyRevenue() {

        List<OrganizerEarning> earnings = earningRepository.findAll();

        Map<String, Double> grouped = earnings.stream()
                .filter(e -> e.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        e -> e.getCreatedAt().toLocalDate().toString(),
                        Collectors.summingDouble(OrganizerEarning::getOrganizerAmount)
                ));

        List<RevenueChartDTO> result = grouped.entrySet()
                .stream()
                .map(e -> new RevenueChartDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(RevenueChartDTO::getLabel))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Daily revenue", false, true, result)
        );
    }

    @GetMapping("/monthly-revenue")
    public ResponseEntity<ApiResponse<List<RevenueChartDTO>>> getMonthlyRevenue() {

        List<OrganizerEarning> earnings = earningRepository.findAll();

        Map<String, Double> grouped = earnings.stream()
                .filter(e -> e.getCreatedAt() != null)
                .collect(Collectors.groupingBy(
                        e -> e.getCreatedAt().getYear() + "-" + e.getCreatedAt().getMonthValue(),
                        Collectors.summingDouble(OrganizerEarning::getOrganizerAmount)
                ));

        List<RevenueChartDTO> result = grouped.entrySet()
                .stream()
                .map(e -> new RevenueChartDTO(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(RevenueChartDTO::getLabel))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>("Monthly revenue", false, true, result)
        );
    }

    @GetMapping("/wallet")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getWallet() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User organizer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrganizerEarning> earnings =
                earningRepository.findByOrganizerId(organizer.getId());

        double balance = earnings.stream()
                .filter(e -> e != null && !e.isPaid())
                .mapToDouble(OrganizerEarning::getOrganizerAmount)
                .sum();

        Map<String, Object> data = new HashMap<>();
        data.put("balance", balance);

        return ResponseEntity.ok(
                new ApiResponse<>("Wallet fetched", false, true, data)
        );
    }
}
