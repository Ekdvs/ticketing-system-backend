package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.EmailTemplates.EmailTemplates;
import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.service.EmailService;
import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final Resend resend;

    public EmailServiceImpl(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    @Override
    public void sendEmail(String to, String subject, String htmlBody) {
        try {
            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Ticket Booking <noreply@ekdvs.xyz>")
                    .to(List.of(to))
                    .subject(subject)
                    .html(htmlBody)
                    .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            System.out.println("email faild"+e.getMessage());
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }

    @Override
    public void sendTicket(String toEmail, String name, Booking booking, byte[] pdf) {
        try {
            String html = EmailTemplates.ticketEmail(name, booking);

            Attachment attachment = Attachment.builder()
                    .fileName("ticket.pdf")
                    .content(Base64.getEncoder().encodeToString(pdf))
                    .build();

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("Ticket Booking <noreply@ekdvs.xyz>")
                    .to(List.of(toEmail))
                    .subject("Your Event Ticket 🎟")
                    .html(html)
                    .attachments(List.of(attachment))
                    .build();

            resend.emails().send(params);

        } catch (ResendException e) {
            throw new RuntimeException("Ticket email failed: " + e.getMessage());
        }
    }
}