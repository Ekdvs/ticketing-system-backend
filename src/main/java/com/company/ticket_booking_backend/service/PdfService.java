package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.Event;

public interface PdfService {
    byte[] createPdf(Booking booking,Event event, byte[] qr) throws Exception;


}
