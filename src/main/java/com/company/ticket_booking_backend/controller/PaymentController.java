package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.EventRepository;
import com.company.ticket_booking_backend.repository.OrganizerEarningRepository;
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

    @Autowired private BookingService bookingService;
    @Autowired private OrganizerEarningRepository earningRepository;
    @Autowired private EventRepository eventRepository;
    @Autowired private TicketService ticketService;
    @Autowired private UserService userService;

    @Value("${payhere.merchant.id}")
    private String MERCHANT_ID;

    @Value("${payhere.merchant.secret}")
    private String MERCHANT_SECRET;

    @Value("${FRONTEND_URL}")
    private String FRONTEND_URL;

    @Value("${BACKEND_URL}")
    private String BACKEND_URL;

    // ================= CREATE PAYMENT =================
    @PostMapping("/create")
    public Map<String, Object> createPayment(@RequestParam String bookingId) {

        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null)            throw new RuntimeException("Booking not found: " + bookingId);
        if (booking.getTotalPrice() <= 0) throw new RuntimeException("Invalid total price");
        if (booking.getUserId() == null)  throw new RuntimeException("UserId is null in booking");

        User user = userService.getUserById(booking.getUserId());
        if (user == null) throw new RuntimeException("User not found: " + booking.getUserId());

        // Amount must be formatted as "XXXX.XX" — used as-is in hash and in form
        String amount = String.format("%.2f", booking.getTotalPrice());

        // Hash = MD5( merchantId + orderId + amount + currency + MD5(secret).toUpperCase() ).toUpperCase()
        String hash = generateHash(MERCHANT_ID, booking.getBookingId(), amount, "LKR");

        // Null-safe first_name fallback — PayHere requires a non-null value
        String firstName = (user.getFirstName() != null && !user.getFirstName().isBlank())
                ? user.getFirstName()
                : "Customer";

        Map<String, Object> payHere = new HashMap<>();
        payHere.put("merchant_id",  MERCHANT_ID);
        payHere.put("return_url",   FRONTEND_URL + "/success");
        payHere.put("cancel_url",   FRONTEND_URL + "/cancel");
        payHere.put("notify_url",   BACKEND_URL  + "/api/payments/notify");
        payHere.put("order_id",     booking.getBookingId());
        payHere.put("items",        "Event Ticket Booking");
        payHere.put("currency",     "LKR");
        payHere.put("amount",       amount);
        payHere.put("first_name",   firstName);
        payHere.put("last_name",    "");          // PayHere accepts empty string
        payHere.put("email",        user.getEmail());
        payHere.put("phone",        "");          // optional but avoids PayHere warnings
        payHere.put("address",      "");
        payHere.put("city",         "");
        payHere.put("country",      "Sri Lanka");
        payHere.put("hash",         hash);

        System.out.println("=== PAYHERE DEBUG ===");
        System.out.println("MERCHANT_ID    : [" + MERCHANT_ID + "]");
        System.out.println("MERCHANT_SECRET: [" + MERCHANT_SECRET + "]");
        System.out.println("AMOUNT         : [" + amount + "]");
        System.out.println("ORDER_ID       : [" + booking.getBookingId() + "]");
        System.out.println("HASH           : [" + hash + "]");
        System.out.println("====================");

        return payHere;
    }

    // ================= PAYMENT NOTIFY =================
    @PostMapping("/notify")
    public ResponseEntity<ApiResponse> paymentNotify(@RequestParam Map<String, String> params) {

        String orderId    = params.get("order_id");
        String statusCode = params.get("status_code");
        String paymentId  = params.get("payment_id");

        if (orderId == null || statusCode == null) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Invalid payment data", true, false, null));
        }

        switch (statusCode) {
            case "2":  // SUCCESS
                Booking booking = bookingService.updatePaymentSuccess(orderId, paymentId);
                ticketService.generateFullTicket(booking);
                break;
            case "0":  // PENDING
                bookingService.updatePaymentPending(orderId);
                break;
            case "-1": // CANCELED
                bookingService.updatePaymentCanceled(orderId);
                break;
            case "-2": // FAILED
                bookingService.updatePaymentFailed(orderId);
                break;
            case "-3": // CHARGEBACK
                bookingService.updatePaymentChargeback(orderId);
                ticketService.revokeTicket(orderId);
                break;
            default:
                System.out.println("Unknown payment status: " + statusCode);
        }

        return ResponseEntity.ok(
                new ApiResponse("Payment notification processed", false, true, null)
        );
    }

    // ================= HASH GENERATOR =================
    // Formula: MD5( merchantId + orderId + amount + currency + MD5(secret).toUpperCase() ).toUpperCase()
    //
    // IMPORTANT:
    //   ✅ amount stays as "XXXX.XX" — do NOT strip the decimal point
    //   ✅ md5(secret) is uppercased BEFORE concatenation
    //   ✅ the whole md5(hashString) is uppercased at the end
    private String generateHash(String merchantId, String orderId, String amount, String currency) {
        try {
            String hashedSecret = md5(MERCHANT_SECRET).toUpperCase();
            String raw          = merchantId + orderId + amount + currency + hashedSecret;
            return md5(raw).toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("Hash generation failed: " + e.getMessage());
        }
    }

    private String md5(String input) throws Exception {
        MessageDigest md  = MessageDigest.getInstance("MD5");
        byte[]        raw = md.digest(input.getBytes("UTF-8")); // explicit UTF-8 charset
        Formatter     fmt = new Formatter();
        for (byte b : raw) fmt.format("%02x", b);
        return fmt.toString();
    }
}