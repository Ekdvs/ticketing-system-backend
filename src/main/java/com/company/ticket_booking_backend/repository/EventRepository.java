package com.company.ticket_booking_backend.repository;

import com.company.ticket_booking_backend.model.Category;
import com.company.ticket_booking_backend.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    Page<Event> findByCategory(Category category, Pageable pageable);

    Page<Event> findByPriceBetween(double min, double max, Pageable pageable);

    Page<Event> findByEventDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    Page<Event> findByCategoryAndPriceBetweenAndEventDateTimeBetween(
            Category category,
            double min,
            double max,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
    List <Event> findByOrganizerId(String id);
}
