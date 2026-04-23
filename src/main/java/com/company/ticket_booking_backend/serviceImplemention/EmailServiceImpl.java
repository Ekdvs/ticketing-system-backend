package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.EmailTemplates.EmailTemplates;
import com.company.ticket_booking_backend.model.Booking;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.EmailService;
import com.company.ticket_booking_backend.service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final org.springframework.mail.javamail.JavaMailSender mailSender;



    public EmailServiceImpl(org.springframework.mail.javamail.JavaMailSender mailSender) {
        this.mailSender = mailSender;

    }



    @Override
    public void sendEmail(String to, String subject, String htmlBody) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // ✅ THIS ENABLES HTML
            helper.setFrom("yourgmail@gmail.com");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }

    // 🎟 SEND PDF TICKET
    @Override
    public void sendTicket(String toEmail,String name, Booking booking, byte[] pdf) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Your Event Ticket 🎟");



            String html = EmailTemplates.ticketEmail(
                    name,
                    booking // 🔥 Cloudinary URL
            );

            helper.setText(html, true);

            helper.addAttachment("ticket.pdf", new ByteArrayResource(pdf));

            helper.setFrom("yourgmail@gmail.com");

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Ticket email failed", e);
        }
    }

}