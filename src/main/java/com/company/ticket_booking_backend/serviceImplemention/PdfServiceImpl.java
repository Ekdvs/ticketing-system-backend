package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.service.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] createPdf(Booking booking, Event event, byte[] qr) throws Exception {

        // QR CODE
        String qrBase64 = Base64.getEncoder().encodeToString(qr);

        // LOGO (SAFE CLASSLOADER WAY)
        ClassPathResource logoResource = new ClassPathResource("static/logo.png");
        byte[] logoBytes;

        try (InputStream is = logoResource.getInputStream()) {
            logoBytes = is.readAllBytes();
        }

        String logoBase64 = Base64.getEncoder().encodeToString(logoBytes);

        // DATE/TIME
        String eventDate = "";
        String eventTime = "";

        if (event.getEventDateTime() != null) {
            eventDate = event.getEventDateTime()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
            eventTime = event.getEventDateTime()
                    .format(DateTimeFormatter.ofPattern("hh:mm a"));
        }

        String html = """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"/>
<style>
body { font-family: Arial; background:#f0f4ff; padding:40px; }
.ticket { background:#fff; border-radius:18px; border:1px solid #dde3f0; width:560px; margin:auto; overflow:hidden; }
.header { background:#4f46e5; padding:28px; color:white; }
.valid-badge { background:#22c55e; padding:6px 14px; border-radius:20px; font-size:10px; }
.meta-bar { background:#eef2ff; padding:14px 32px; }
.body { padding:26px 32px; }
.qr-wrap { border:2px solid #e0e7ff; padding:10px; }
.footer { padding:14px 32px; background:#fafbff; }
</style>
</head>

<body>
<div class="ticket">

<div class="header">
<table width="100%">
<tr>
<td>
<div>{{EVENT_CATEGORY}} • {{EVENT_SUBCATEGORY}}</div>
<h2>{{EVENT_TITLE}}</h2>
<div>{{EVENT_VENUE}}, {{EVENT_LOCATION}}</div>
</td>

<td>
<img src="data:image/png;base64,{{LOGO_BASE64}}" style="height:60px;"/>
</td>

<td align="right">
<span class="valid-badge">✔ Confirmed</span>
</td>
</tr>
</table>
</div>

<div class="meta-bar">
<b>Date:</b> {{EVENT_DATE}} |
<b>Time:</b> {{EVENT_TIME}} |
<b>Venue:</b> {{EVENT_VENUE}} |
<b>Payment:</b> {{PAYMENT_STATUS}}
</div>

<div class="body">
<table width="100%">
<tr>
<td>

<p><b>Booking ID:</b> {{BOOKING_ID}}</p>
<p><b>Event ID:</b> {{EVENT_ID}}</p>
<p><b>Tickets:</b> {{QUANTITY}}</p>
<p><b>Total:</b> LKR {{PRICE}}</p>

</td>

<td align="center">
<div class="qr-wrap">
<img src="data:image/png;base64,{{QR_BASE64}}" width="120"/>
</div>
<p>Scan at entry</p>
</td>
</tr>
</table>
</div>

<hr/>

<div class="footer">
Powered by <b>TicketSystem</b><br/>
Payment Ref: {{PAYMENT_ID}}
</div>

</div>
</body>
</html>
""";

        // SAFE REPLACEMENTS
        html = html
                .replace("{{BOOKING_ID}}", String.valueOf(booking.getBookingId()))
                .replace("{{EVENT_ID}}", String.valueOf(booking.getEventId()))
                .replace("{{QUANTITY}}", String.valueOf(booking.getQuantity()))
                .replace("{{PRICE}}", String.valueOf(booking.getTotalPrice()))
                .replace("{{PAYMENT_STATUS}}", safe(booking.getPaymentStatus()))
                .replace("{{PAYMENT_ID}}", safe(booking.getPaymentId()))
                .replace("{{EVENT_TITLE}}", safe(event.getTitle()))
                .replace("{{EVENT_LOCATION}}", safe(event.getLocation()))
                .replace("{{EVENT_VENUE}}", safe(event.getVenue()))
                .replace("{{EVENT_CATEGORY}}", event.getCategory() != null ? event.getCategory().toString() : "Event")
                .replace("{{EVENT_SUBCATEGORY}}", event.getSubCategory() != null ? event.getSubCategory().toString() : "")
                .replace("{{EVENT_DATE}}", eventDate)
                .replace("{{EVENT_TIME}}", eventTime)
                .replace("{{QR_BASE64}}", qrBase64)
                .replace("{{LOGO_BASE64}}", logoBase64);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();

        // IMPORTANT FIX: correct base URI for resources
        builder.withHtmlContent(html, "classpath:/static/");
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }

    private String safe(String value) {
        return value != null ? value : "N/A";
    }
}