package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.OrganizerWallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrganizerWalletRepository extends MongoRepository<OrganizerWallet, String> {

    Optional<OrganizerWallet> findByOrganizerId(String organizerId);
}
