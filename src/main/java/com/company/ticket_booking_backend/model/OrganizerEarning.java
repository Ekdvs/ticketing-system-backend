package com.company.ticket_booking_backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "earnings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizerEarning {

    @Id
    private String id;

    private String organizerId;
    private String eventId;
    private String bookingId;

    private double totalAmount;
    private double platformFee;
    private double organizerAmount;

    private boolean paid; // payout status

    private LocalDateTime createdAt;
}