package com.company.ticket_booking_backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "wallets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizerWallet {

    @Id
    private String id;

    private String organizerId;

    private double totalEarnings;
    private double availableBalance;
    private double withdrawnAmount;

    private double lockedBalance; // 🔥 NEW (optional but recommended)

    private LocalDateTime updatedAt;
}