package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.BookingRepository;
import com.company.ticket_booking_backend.security.QRTokenUtil;
import com.company.ticket_booking_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired private QRService qrService;
    @Autowired private PdfService pdfService;
    @Autowired private EmailService emailService;
    @Autowired private UserService userService;
    @Autowired private EventService eventService;
    @Autowired private CloudinaryService cloudinaryService;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void generateFullTicket(Booking booking) {

        try {
            String token = QRTokenUtil.generateToken(
                    booking.getBookingId(),
                    booking.getUserId()
            );

            System.out.println("QR TOKEN: " + token);

            byte[] qr = qrService.generateQR(token);

            Event event = eventService.getEventById(booking.getEventId());
            System.out.println(event);

            byte[] pdf = pdfService.createPdf(booking, event,qr);

            String pdfUrl = cloudinaryService.uploadPdf(pdf, booking.getBookingId());

            booking.setTicketUrl(pdfUrl);
            //System.out.println("PDF URL: " + pdfUrl);

            bookingRepository.save(booking);


            User user = userService.getUserById(booking.getUserId());



            emailService.sendTicket(user.getEmail(),user.getFirstName(),booking, pdf);

        } catch (Exception e) {
            System.out.println("Error generating ticket: " + e.getMessage());
            throw new RuntimeException("Ticket generation failed", e);
        }
    }
}