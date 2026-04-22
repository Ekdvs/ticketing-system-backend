package com.company.ticket_booking_backend.service;

public interface QRService {
    byte[] generateQR(String text) throws Exception;
}
