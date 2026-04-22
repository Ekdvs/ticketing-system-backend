package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.EntryLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntryLogRepository extends MongoRepository<EntryLog, String> {

    List<EntryLog> findByEventId(String eventId);

    List<EntryLog> findByScannedBy(String scannedBy);
}