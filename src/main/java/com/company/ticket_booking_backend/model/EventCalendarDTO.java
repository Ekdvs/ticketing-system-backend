package com.company.ticket_booking_backend.model;

import java.time.LocalDateTime;

public class EventCalendarDTO {
    private String id;
    private String title;
    private LocalDateTime eventDateTime;
    private double price;
    private boolean active;
    private String location;

    public EventCalendarDTO(String id, String title, LocalDateTime eventDateTime,
                            double price, boolean active, String location) {
        this.id = id;
        this.title = title;
        this.eventDateTime = eventDateTime;
        this.price = price;
        this.active = active;
        this.location = location;
    }

    // getters/setters
}