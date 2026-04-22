package com.company.ticket_booking_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entry_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntryLog {

    @Id
    private String id;

    private String bookingId;
    private String eventId;
    private String userId;

    private String scannedBy; // admin or organizer
    private LocalDateTime scannedAt;

    private String status; // SUCCESS / FAILED
}
