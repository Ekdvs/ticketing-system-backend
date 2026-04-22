package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.BookingService;
import com.company.ticket_booking_backend.service.TicketService;

import com.company.ticket_booking_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private BookingService bookingService;


    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Value("${payhere.merchant.id}")
    private String MERCHANT_ID;

    @Value("${payhere.merchant.secret}")
    private String MERCHANT_SECRET;

    // ================= CREATE PAYMENT =================
    @PostMapping("/create")
    public Map<String, Object> createPayment(@RequestParam String bookingId) {

        Booking booking = bookingService.getBookingById(bookingId);

        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }



        String amount = String.format("%.2f", booking.getTotalPrice());

        // 🔐 Generate PayHere HASH (IMPORTANT)
        String hash = generateHash(
                MERCHANT_ID,
                booking.getBookingId(),
                amount,
                "LKR"
        );

        Map<String, Object> payHere = new HashMap<>();

        payHere.put("merchant_id", MERCHANT_ID);
        payHere.put("return_url", "http://localhost:3000/success");
        payHere.put("cancel_url", "http://localhost:3000/cancel");
        payHere.put("notify_url", "http://localhost:8080/api/payments/notify");

        payHere.put("order_id", booking.getBookingId());
        payHere.put("items", "Event Ticket Booking");
        payHere.put("currency", "LKR");
        payHere.put("amount", amount);

        //get user
        User user = userService.getUserById(booking.getUserId());

        payHere.put("first_name", user.getFirstName());
        payHere.put("email", user.getEmail());

        payHere.put("hash", hash); // 🔐 VERY IMPORTANT

        return payHere;
    }

    // ================= PAYMENT NOTIFY =================
    @PostMapping("/notify")
    public ResponseEntity<ApiResponse> paymentNotify(@RequestParam Map<String, String> params) {

        String orderId = params.get("order_id");
        String statusCode = params.get("status_code");
        String paymentId = params.get("payment_id");

        System.out.println("STEP 0: Notify received -> " + params);

        // ✅ SUCCESS PAYMENT
        if ("2".equals(statusCode)) {
            System.out.println("STEP 1: Payment SUCCESS");

            Booking booking = bookingService.updatePaymentSuccess(orderId, paymentId);

            System.out.println("STEP 2: Booking updated");
            // 🎟 Generate ticket after payment success
            ticketService.generateFullTicket(booking);
            System.out.println("STEP 3: Ticket generation called");

            System.out.println("Payment SUCCESS for booking: " + orderId);
        } else {
            System.out.println("Payment FAILED or PENDING: " + orderId);
        }

        return ResponseEntity.ok(new ApiResponse("Payment notification received", false, true, null));
    }

    // ================= HASH GENERATOR =================
    private String generateHash(String merchantId, String orderId, String amount, String currency) {
        try {

            String formattedAmount = amount.replaceAll("\\.", "");

            String hashString = merchantId + orderId + formattedAmount + currency + md5(MERCHANT_SECRET);

            return md5(hashString).toUpperCase();

        } catch (Exception e) {
            throw new RuntimeException("Hash generation failed");
        }
    }

    private String md5(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(input.getBytes());

        Formatter formatter = new Formatter();
        for (byte b : digest) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}