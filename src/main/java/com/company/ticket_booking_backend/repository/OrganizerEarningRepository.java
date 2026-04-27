package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.OrganizerEarning;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrganizerEarningRepository extends MongoRepository<OrganizerEarning, String> {

    List<OrganizerEarning> findByOrganizerId(String organizerId);
    List<OrganizerEarning> findByOrganizerIdAndCreatedAtBetween(
            String organizerId,
            LocalDateTime start,
            LocalDateTime end
    );
}