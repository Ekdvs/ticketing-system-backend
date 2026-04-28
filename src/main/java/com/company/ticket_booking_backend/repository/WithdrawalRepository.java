package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.WithdrawalRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends MongoRepository<WithdrawalRequest, String> {
    List<WithdrawalRequest> findByOrganizerId(String organizerId);
}