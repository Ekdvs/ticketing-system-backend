package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailVerificationToken(String token);
}
