package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.*;
import com.company.ticket_booking_backend.repository.BookingRepository;
import com.company.ticket_booking_backend.repository.EventRepository;
import com.company.ticket_booking_backend.repository.OrganizerEarningRepository;
import com.company.ticket_booking_backend.repository.UserRepository;
import com.company.ticket_booking_backend.service.BookingService;
import com.company.ticket_booking_backend.service.EventService;
import com.company.ticket_booking_backend.service.NotificationService;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private OrganizerEarningRepository earningRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;



    private String bookingId;
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Booking createBooking(Booking booking,String userId){

        Event event = eventService.getEventById(booking.getEventId());

        //get user
        User user = userService.getUserByEmail(userId);
        if(booking.getQuantity()>event.getAvailableTickets()){
            throw new RuntimeException("Not enough tickets available");

        }

        //generate booking id
        String id = UUID.randomUUID().toString();
        booking.setBookingId(id);
        booking.setPaymentStatus("PENDING");
        booking.setTicketStatus("PENDING");
        booking.setUserId(user.getId());
        booking.setTicketUsed(false);

        // 🔥 CALCULATE PRICE (IMPORTANT FIX)


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

        if (booking.getPaymentStatus().equals("SUCCESS")) {
            throw new RuntimeException("Payment already successful");
        }

        Event event = eventService.getEventById(booking.getEventId());

        // 🎟 Reduce tickets
        int quantity = booking.getQuantity();
        event.setAvailableTickets(event.getAvailableTickets() - quantity);
        eventRepository.save(event);

        // 💳 Update booking
        booking.setPaymentId(paymentId);
        booking.setPaymentStatus("SUCCESS");
        booking.setTicketStatus("ISSUED");

        bookingRepository.save(booking);

        // =============================
        // 💰 EARNINGS LOGIC (NEW)
        // =============================

        double totalAmount = booking.getTotalPrice();

        double platformFee = totalAmount * 0.10; // 10%
        double organizerAmount = totalAmount - platformFee;

        OrganizerEarning earning = OrganizerEarning.builder()
                .organizerId(event.getOrganizerId())
                .eventId(event.getId())
                .bookingId(booking.getBookingId())
                .totalAmount(totalAmount)
                .platformFee(platformFee)
                .organizerAmount(organizerAmount)
                .paid(false)
                .createdAt(LocalDateTime.now())
                .build();

        earningRepository.save(earning);

        // 1️⃣ Notify the USER their payment succeeded
        notificationService.sendToUser(
                booking.getUserId(),
                "BOOKING_PAID",
                "Payment Successful 🎉",
                "Your booking #" + booking.getBookingId() + " is confirmed. Enjoy the event!"
        );

// 2️⃣ Notify ADMIN a new booking payment came in
        notificationService.sendToAdmin(
                "BOOKING_PAID",
                "New Booking Payment 💳",
                "A payment of Rs " + totalAmount + " was received for booking #" + booking.getBookingId()
        );

        return booking;
    }

    @Override
    public void updatePaymentPending(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));



        if (booking.getPaymentStatus().equals("SUCCESS")) {
            throw new RuntimeException("Cannot set to PENDING. Payment already successful for this booking");
        }

        booking.setPaymentStatus(String.valueOf(PaymentStatus.PENDING));
        bookingRepository.save(booking);
    }


    @Override
    public void updatePaymentCanceled(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));



        if (booking.getPaymentStatus().equals("SUCCESS")){
            throw new RuntimeException("Cannot set to CANCELED. Payment already successful for this booking");
        }

        booking.setPaymentStatus(String.valueOf(PaymentStatus.CANCELED));
        bookingRepository.save(booking);
    }

    public void updatePaymentFailed(String bookingId) {
        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));



        if (booking.getPaymentStatus().equals("SUCCESS")){
            throw new RuntimeException("Cannot set to FAILED. Payment already successful for this booking");
        }
        booking.setPaymentStatus(String.valueOf(PaymentStatus.FAILED));
        bookingRepository.save(booking);
    }

    @Override
    public void updatePaymentChargeback(String bookingId) {

        Booking booking = bookingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));



        if (booking.getPaymentStatus().equals("SUCCESS")){
            throw new RuntimeException("Cannot set to CHARGEDBACK. Payment already successful for this booking");
        }

        booking.setPaymentStatus(String.valueOf(PaymentStatus.CHARGEDBACK));
        bookingRepository.save(booking);
    }



    



}
