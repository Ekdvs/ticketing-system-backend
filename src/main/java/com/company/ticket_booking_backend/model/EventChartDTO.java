package com.company.ticket_booking_backend.model;


public class EventChartDTO {

    private String eventId;
    private String eventTitle;
    private long totalTickets;
    private long usedTickets;
    private double revenue;

    public EventChartDTO(String eventId, String eventTitle, long totalTickets, long usedTickets, double revenue) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.totalTickets = totalTickets;
        this.usedTickets = usedTickets;
        this.revenue = revenue;
    }

    // getters

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public long getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(long totalTickets) {
        this.totalTickets = totalTickets;
    }

    public long getUsedTickets() {
        return usedTickets;
    }

    public void setUsedTickets(long usedTickets) {
        this.usedTickets = usedTickets;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
