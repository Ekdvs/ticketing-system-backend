package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.BookingRepository;
import com.company.ticket_booking_backend.service.BookingService;
import com.company.ticket_booking_backend.service.EventService;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;

    private String bookingId;

    @Override
    public Booking createBooking(Booking booking,String userId){

        //get user
        User user = userService.getUserByEmail(userId);

        //generate booking id
        String id = UUID.randomUUID().toString();
        booking.setBookingId(id);
        booking.setPaymentStatus("PENDING");
        booking.setTicketStatus("PENDING");
        booking.setUserId(user.getId());
        booking.setTicketUsed(false);

        // 🔥 CALCULATE PRICE (IMPORTANT FIX)
        Event event = eventService.getEventById(booking.getEventId());

        double totalPrice = event.getPrice() * booking.getQuantity();
        booking.setTotalPrice(totalPrice);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookingRepository.findByBookingId(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

    }

    @Override
    public List<Booking>getAllBookings() {
        return bookingRepository.findAll();
    }
    @Override
    public Booking updatePaymentSuccess(String bookingId, String paymentId) {

        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setPaymentStatus(paymentId);
        booking.setPaymentStatus("SUCCESS");
        booking.setTicketStatus("ISSUED");


        return bookingRepository.save(booking);
    }



}
