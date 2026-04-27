package com.company.ticket_booking_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "withdrawals")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawalRequest {

    @Id
    private String id;

    private String organizerId;
    private double amount;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime createdAt;
}