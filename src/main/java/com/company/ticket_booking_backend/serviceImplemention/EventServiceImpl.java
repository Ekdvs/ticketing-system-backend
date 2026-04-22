package com.company.ticket_booking_backend.serviceImplemention;

import com.company.ticket_booking_backend.model.Category;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.model.SubCategory;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.repository.EventRepository;
import com.company.ticket_booking_backend.service.CloudinaryService;
import com.company.ticket_booking_backend.service.EventService;
import com.company.ticket_booking_backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final CloudinaryService cloudinaryService;
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventServiceImpl(EventRepository eventRepository,
                            CloudinaryService cloudinaryService, UserService userService) {
        this.eventRepository = eventRepository;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    // ================= CREATE =================
    @Override
    public Event createEvent(Event event, List<MultipartFile> images) {

        if (!SubCategory.isValid(event.getCategory(), event.getSubCategory())) {
            throw new RuntimeException("Invalid subcategory");
        }

        String organizeremail = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User scanner = userService.getUserByEmail(organizeremail) ;

        event.setOrganizerId(scanner.getId());


        event.initialize();

        // ✅ MULTI IMAGE UPLOAD
        if (images != null && !images.isEmpty()) {

            List<String> imageUrls = images.stream()
                    .map(cloudinaryService::uploadImage)
                    .toList();

            event.setImageUrls(imageUrls);
        }

        return eventRepository.save(event);
    }

    // ================= GET =================
    @Override
    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // ================= DELETE =================
    @Override
    public void deleteEvent(String id) {
        Event event = getEventById(id);

        // delete images from cloud
        if (event.getImageUrls() != null) {
            event.getImageUrls().forEach(cloudinaryService::deleteImage);
        }

        eventRepository.delete(event);
    }

    // ================= TOGGLE =================
    @Override
    public Event toggleActive(String id) {
        Event event = getEventById(id);
        event.setActive(!event.isActive());
        return eventRepository.save(event);
    }

    // ================= UPDATE =================
    @Override
    public Event updateEvent(String id, Event updatedEvent, List<MultipartFile> images) {

        if (!SubCategory.isValid(updatedEvent.getCategory(), updatedEvent.getSubCategory())) {
            throw new RuntimeException("Invalid subcategory");
        }

        Event event = getEventById(id);

        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setCategory(updatedEvent.getCategory());
        event.setSubCategory(updatedEvent.getSubCategory());
        event.setLocation(updatedEvent.getLocation());
        event.setVenue(updatedEvent.getVenue());
        event.setEventDateTime(updatedEvent.getEventDateTime());
        event.setPrice(updatedEvent.getPrice());
        event.setTotalTickets(updatedEvent.getTotalTickets());

        // ✅ MULTI IMAGE UPDATE
        if (images != null && !images.isEmpty()) {

            // delete old images
            if (event.getImageUrls() != null) {
                event.getImageUrls().forEach(cloudinaryService::deleteImage);
            }

            List<String> imageUrls = images.stream()
                    .map(cloudinaryService::uploadImage)
                    .toList();

            event.setImageUrls(imageUrls);
        }

        return eventRepository.save(event);
    }

    // ================= SEARCH =================
    @Override
    public Page<Event> searchEvents(
            Category category,
            Double minPrice,
            Double maxPrice,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int page,
            int size
    ) {

        PageRequest pageable = PageRequest.of(page, size);

        if (category != null && minPrice != null && maxPrice != null && startDate != null && endDate != null) {
            return eventRepository.findByCategoryAndPriceBetweenAndEventDateTimeBetween(
                    category, minPrice, maxPrice, startDate, endDate, pageable
            );
        }

        if (category != null) {
            return eventRepository.findByCategory(category, pageable);
        }

        if (minPrice != null && maxPrice != null) {
            return eventRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        }

        if (startDate != null && endDate != null) {
            return eventRepository.findByEventDateTimeBetween(startDate, endDate, pageable);
        }

        return eventRepository.findAll(pageable);
    }

    // ================= GET ALL =================
    @Override
    public Page<Event>getAllEvents(int page, int size){
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return eventRepository.findAll(pageable);
    }
}