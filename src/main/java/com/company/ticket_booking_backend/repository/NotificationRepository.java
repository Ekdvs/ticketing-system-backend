// repository/NotificationRepository.java
package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByUserIdOrderByCreatedAtDesc(String userId);

    List<Notification> findByRoleAndUserIdIsNullOrderByCreatedAtDesc(String role);

    long countByUserIdAndReadFalse(String userId);

    long countByRoleAndUserIdIsNullAndReadFalse(String role);
}