// service/NotificationService.java
package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.Notification;
import java.util.List;

public interface NotificationService {
    void sendToUser(String userId, String type, String title, String message);
    void sendToAdmin(String type, String title, String message);
    void sendToOrganizer(String organizerId, String type, String title, String message);
    List<Notification> getMyNotifications(String userId);
    List<Notification> getAdminNotifications();
    void markAsRead(String notificationId);
    long getUnreadCount(String userId);
    long getAdminUnreadCount();
}