package com.company.ticket_booking_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevenueChartDTO {
    private String label; // date or month
    private double amount;
}