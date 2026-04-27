// controller/NotificationController.java
package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    // ── Get my notifications (organizer / user)
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<Notification>>> getMyNotifications() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        List<Notification> list = notificationService.getMyNotifications(user.getId());
        return ResponseEntity.ok(new ApiResponse<>("Notifications", false, true, list));
    }

    // ── Get admin notifications
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<List<Notification>>> getAdminNotifications() {
        List<Notification> list = notificationService.getAdminNotifications();
        return ResponseEntity.ok(new ApiResponse<>("Admin Notifications", false, true, list));
    }

    // ── Unread count (user/organizer)
    @GetMapping("/unread-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getUnreadCount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        long count = notificationService.getUnreadCount(user.getId());
        return ResponseEntity.ok(new ApiResponse<>("Unread count", false, true, Map.of("count", count)));
    }

    // ── Admin unread count
    @GetMapping("/admin/unread-count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getAdminUnreadCount() {
        long count = notificationService.getAdminUnreadCount();
        return ResponseEntity.ok(new ApiResponse<>("Admin unread count", false, true, Map.of("count", count)));
    }

    // ── Mark as read
    @PatchMapping("/read/{id}")
    public ResponseEntity<ApiResponse> markRead(@PathVariable String id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(new ApiResponse<>("Marked as read", false, true, null));
    }
}