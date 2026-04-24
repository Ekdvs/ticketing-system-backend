package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.service.PdfService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] createPdf(Booking booking, Event event, byte[] qr) throws Exception {

        String qrBase64 = Base64.getEncoder().encodeToString(qr);
        byte[] logoBytes = Files.readAllBytes(
                Paths.get("src/main/resources/static/logo.png")
        );

        String logoBase64 = Base64.getEncoder().encodeToString(logoBytes);

        String eventDate = "";
        String eventTime = "";

        if (event.getEventDateTime() != null) {
            eventDate = event.getEventDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
            eventTime = event.getEventDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        }

        String html = """
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8"/>
<style>

  * { margin: 0; padding: 0; }

  body {
    font-family: Arial, Helvetica, sans-serif;
    background-color: #f0f4ff;
    padding: 40px;
  }

  .page-wrap {
    width: 560px;
    margin: 0 auto;
  }

  .ticket {
    background-color: #ffffff;
    border-radius: 18px;
    overflow: hidden;
    border: 1px solid #dde3f0;
    width: 560px;
  }

  .header {
    background-color: #4f46e5;
    padding: 28px 32px 24px 32px;
    width: 100%;
  }

  .header-table {
    width: 100%;
    border-collapse: collapse;
  }

  .event-category {
    font-size: 10px;
    letter-spacing: 3px;
    color: #c4b5fd;
    text-transform: uppercase;
    margin-bottom: 6px;
  }

  .event-title {
    font-size: 22px;
    font-weight: bold;
    color: #ffffff;
    margin-bottom: 4px;
  }

  .event-sub {
    font-size: 11px;
    color: #a5b4fc;
  }

  .valid-badge {
    background-color: #22c55e;
    color: #ffffff;
    font-size: 10px;
    padding: 6px 14px;
    border-radius: 20px;
    font-weight: bold;
  }

  .meta-bar {
    background-color: #eef2ff;
    padding: 14px 32px;
    border-bottom: 1px solid #e0e7ff;
  }

  .meta-table {
    width: 100%;
  }

  .meta-item {
    padding-right: 20px;
  }

  .meta-icon-label {
    font-size: 9px;
    color: #6366f1;
    font-weight: bold;
  }

  .meta-value {
    font-size: 12px;
    font-weight: bold;
    color: #1e1b4b;
  }

  .tear-row {
    background-color: #f8faff;
    height: 20px;
    border-top: 2px dashed #c7d2fe;
    border-bottom: 2px dashed #c7d2fe;
  }

  .body {
    padding: 26px 32px;
  }

  .body-table {
    width: 100%;
  }

  .info-cell {
    padding-right: 24px;
  }

  .qr-cell {
    text-align: center;
    width: 160px;
  }

  .section-title {
    font-size: 9px;
    color: #6366f1;
    font-weight: bold;
    margin-bottom: 14px;
    border-bottom: 2px solid #e0e7ff;
  }

  .info-grid {
    width: 100%;
  }

  .info-grid td {
    padding-bottom: 16px;
    width: 50%;
  }

  .info-item {
    background-color: #f5f7ff;
    padding: 10px;
    border-left: 3px solid #6366f1;
  }

  .info-label {
    font-size: 9px;
    color: #6b7280;
  }

  .info-value {
    font-size: 13px;
    font-weight: bold;
  }

  .info-value-price {
    color: #4f46e5;
    font-weight: bold;
  }

  .info-value-qty {
    color: #059669;
    font-weight: bold;
  }

  .qr-wrap {
    background-color: #ffffff;
    padding: 10px;
    border: 2px solid #e0e7ff;
  }

  .qr-label {
    font-size: 9px;
    color: #6b7280;
    margin-top: 10px;
  }

  .qr-hint {
    font-size: 8px;
    color: #9ca3af;
  }

  .divider {
    border-top: 1px solid #e5e7eb;
    margin: 0 32px;
  }

  .footer {
    padding: 14px 32px;
    background-color: #fafbff;
  }

  .footer-table {
    width: 100%;
  }

  .footer-brand {
    font-size: 11px;
    color: #9ca3af;
  }

  .footer-brand-highlight {
    color: #4f46e5;
    font-weight: bold;
  }

  .footer-hint {
    font-size: 10px;
    text-align: right;
  }

  .footer-id {
    font-size: 9px;
    color: #d1d5db;
  }

</style>
</head>
<body>

<div class="page-wrap">
<div class="ticket">

  <div class="header">
    <table class="header-table">
      <tr>
        <td>
          <!-- FIXED HERE -->
          <div class="event-category">{{EVENT_CATEGORY}} • {{EVENT_SUBCATEGORY}}</div>
          <div class="event-title">{{EVENT_TITLE}}</div>
          <div class="event-sub">{{EVENT_VENUE}}, {{EVENT_LOCATION}}</div>
        </td>
        <td>
            <img src="data:image/png;base64,{{LOGO_BASE64}}"
                 style="height:60px; margin-bottom:10px;" />
        </td>
        <td style="text-align:right;">
          <span class="valid-badge">&#10003; Confirmed</span>
        </td>
      </tr>
    </table>
  </div>

  <div class="meta-bar">
    <table class="meta-table">
      <tr>
        
        <td class="meta-item">
          <div class="meta-icon-label">Date</div>
          <div class="meta-value">{{EVENT_DATE}}</div>
        </td>
        <td class="meta-item">
          <div class="meta-icon-label">Time</div>
          <div class="meta-value">{{EVENT_TIME}}</div>
        </td>
        <td class="meta-item">
          <div class="meta-icon-label">Venue</div>
          <div class="meta-value">{{EVENT_VENUE}}</div>
        </td>
        <td class="meta-item">
          <div class="meta-icon-label">Payment</div>
          <div class="meta-value">{{PAYMENT_STATUS}}</div>
        </td>
      </tr>
    </table>
  </div>

  <div class="tear-row"></div>

  <div class="body">
    <table class="body-table">
      <tr>

        <td class="info-cell">
          <div class="section-title">Booking Details</div>

          <table class="info-grid">
            <tr>
              <td>
                <div class="info-item">
                  <div class="info-label">Booking ID</div>
                  <div class="info-value">{{BOOKING_ID}}</div>
                </div>
              </td>
              <td>
                <div class="info-item">
                  <div class="info-label">Event ID</div>
                  <div class="info-value">{{EVENT_ID}}</div>
                </div>
              </td>
            </tr>
            <tr>
              <td>
                <div class="info-item">
                  <div class="info-label">Tickets</div>
                  <div class="info-value-qty">{{QUANTITY}}</div>
                </div>
              </td>
              <td>
                <div class="info-item">
                  <div class="info-label">Total Paid</div>
                  <div class="info-value-price">LKR {{PRICE}}</div>
                </div>
              </td>
            </tr>
          </table>
        </td>

        <td class="qr-cell">
          <div class="qr-wrap">
            <img src="data:image/png;base64,{{QR_BASE64}}" width="120"/>
          </div>
          <div class="qr-label">Scan at Entry</div>
          <div class="qr-hint">Show this to gate staff</div>
        </td>

      </tr>
    </table>
  </div>

  <hr class="divider"/>

  <div class="footer">
    <table class="footer-table">
      <tr>
        <td>
          <div class="footer-brand">
            Powered by <span class="footer-brand-highlight">TicketSystem</span>
          </div>
          <div class="footer-id">Payment Ref: {{PAYMENT_ID}}</div>
        </td>
        <td class="footer-hint">Present this ticket at the entry gate</td>
      </tr>
    </table>
  </div>

</div>
</div>

</body>
</html>
""";

        // SAFETY FIX
        html = html.replace("&bull;", "•");

        html = html
                .replace("{{BOOKING_ID}}", booking.getBookingId())
                .replace("{{EVENT_ID}}", booking.getEventId())
                .replace("{{QUANTITY}}", String.valueOf(booking.getQuantity()))
                .replace("{{PRICE}}", String.valueOf(booking.getTotalPrice()))
                .replace("{{PAYMENT_STATUS}}", booking.getPaymentStatus() != null ? booking.getPaymentStatus() : "N/A")
                .replace("{{PAYMENT_ID}}", booking.getPaymentId() != null ? booking.getPaymentId() : "N/A")
                .replace("{{EVENT_TITLE}}", event.getTitle() != null ? event.getTitle() : "Event")
                .replace("{{EVENT_LOCATION}}", event.getLocation() != null ? event.getLocation() : "N/A")
                .replace("{{EVENT_VENUE}}", event.getVenue() != null ? event.getVenue() : "N/A")
                .replace("{{EVENT_CATEGORY}}", event.getCategory() != null ? event.getCategory().toString() : "Event")
                .replace("{{EVENT_SUBCATEGORY}}", event.getSubCategory() != null ? event.getSubCategory().toString() : "")
                .replace("{{EVENT_DATE}}", eventDate)
                .replace("{{EVENT_TIME}}", eventTime)
                .replace("{{QR_BASE64}}", qrBase64)
                .replace("{{LOGO_BASE64}}", logoBase64);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, "/"); // IMPORTANT FIX
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }
}