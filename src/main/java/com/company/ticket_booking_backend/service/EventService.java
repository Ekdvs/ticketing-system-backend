package com.company.ticket_booking_backend.service;

import com.company.ticket_booking_backend.model.Category;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.model.EventCalendarDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    Event createEvent(Event event, List<MultipartFile> images);

    Event updateEvent(String id, Event event, List<MultipartFile> images);



    Event getEventById(String id);

    void deleteEvent(String id);

    Event toggleActive(String id);

    Page<Event> searchEvents(
            Category category,
            Double minPrice,
            Double maxPrice,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size
    );

    Page<Event>getAllEvents(int page, int size);

    List<EventCalendarDTO> getCalendarData();
    List<Event> getByOrganizerId(String organizerId);
}
