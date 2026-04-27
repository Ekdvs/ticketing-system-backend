// model/Notification.java
package com.company.ticket_booking_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "notifications")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Notification {

    @Id
    private String id;

    private String userId;       // who receives it
    private String role;         // "ADMIN" | "ORGANIZER" | "USER"
    private String type;         // "BOOKING_PAID" | "WITHDRAW_REQUEST" | "WITHDRAW_APPROVED"
    private String title;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
}