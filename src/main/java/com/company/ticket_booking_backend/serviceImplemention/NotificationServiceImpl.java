// serviceImplemention/NotificationServiceImpl.java
package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.Notification;
import com.company.ticket_booking_backend.repository.NotificationRepository;
import com.company.ticket_booking_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendToUser(String userId, String type, String title, String message) {
        Notification n = Notification.builder()
                .userId(userId)
                .role("USER")
                .type(type)
                .title(title)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(n);
    }

    @Override
    public void sendToAdmin(String type, String title, String message) {
        // No userId — broadcast to all admins by role
        Notification n = Notification.builder()
                .role("ADMIN")
                .type(type)
                .title(title)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(n);
    }

    @Override
    public void sendToOrganizer(String organizerId, String type, String title, String message) {
        Notification n = Notification.builder()
                .userId(organizerId)
                .role("ORGANIZER")
                .type(type)
                .title(title)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        notificationRepository.save(n);
    }

    @Override
    public List<Notification> getMyNotifications(String userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<Notification> getAdminNotifications() {
        return notificationRepository.findByRoleAndUserIdIsNullOrderByCreatedAtDesc("ADMIN");
    }

    @Override
    public void markAsRead(String notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }

    @Override
    public long getUnreadCount(String userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    @Override
    public long getAdminUnreadCount() {
        return notificationRepository.countByRoleAndUserIdIsNullAndReadFalse("ADMIN");
    }
}