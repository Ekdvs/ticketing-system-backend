package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.Category;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.model.User;
import com.company.ticket_booking_backend.service.EventService;
import com.company.ticket_booking_backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/event")
@CrossOrigin("*")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= GET CURRENT USER =================
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(email);
    }

    // ================= CREATE EVENT =================
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Event>> createEvent(
            @RequestPart("event") String eventJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws JsonProcessingException {

        Event event = objectMapper.readValue(eventJson, Event.class);

        User user = getCurrentUser();

        // 🔥 ALWAYS FORCE ORGANIZER ID FROM TOKEN (NOT FRONTEND)
        event.setOrganizerId(user.getId());

        Event saved = eventService.createEvent(event, images);

        return ResponseEntity.status(201).body(
                new ApiResponse<>("Event created", false, true, saved)
        );
    }

    // ================= GET EVENT BY ID =================
    @GetMapping("/getById/{id}")
    public ResponseEntity<ApiResponse<Event>> getEventById(@PathVariable("id") String id){
        if(id==null){
            return new ResponseEntity<>(new ApiResponse<>("Event not found", false, false, null), HttpStatus.NOT_FOUND);
        }
        Event event = eventService.getEventById(id);

        return ResponseEntity.ok(new ApiResponse<>("Event retrieved successfully", false, true, event));
    }

    // ================= EVENT DELETE BY ID =================
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable String id) {

        Event existing = eventService.getEventById(id);
        User user = getCurrentUser();

        if (!existing.getOrganizerId().equals(user.getId())
                && !user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(403)
                    .body(new ApiResponse<>("Forbidden", true, false, null));
        }

        eventService.deleteEvent(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Deleted", false, true, null)
        );
    }

    // ================= EVENT ACTIVE /INACTIVE =================
    @PatchMapping("/toggle-active/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Event>> toggle(@PathVariable String id) {

        Event existing = eventService.getEventById(id);
        User user = getCurrentUser();

        if (!existing.getOrganizerId().equals(user.getId())
                && !user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(403)
                    .body(new ApiResponse<>("Forbidden", true, false, null));
        }

        Event updated = eventService.toggleActive(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Toggled", false, true, updated)
        );
    }

    // ================= EVENT ACTIVE /INACTIVE =================
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Event>> updateEvent(
            @PathVariable String id,
            @RequestPart("event") String eventJson,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) throws JsonProcessingException {

        Event existing = eventService.getEventById(id);

        User user = getCurrentUser();

        // 🔥 SECURITY CHECK: organizer can only update own event
        if (!existing.getOrganizerId().equals(user.getId())
                && !user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(403)
                    .body(new ApiResponse<>("Forbidden", true, false, null));
        }

        Event updated = objectMapper.readValue(eventJson, Event.class);

        Event saved = eventService.updateEvent(id, updated, images);

        return ResponseEntity.ok(
                new ApiResponse<>("Updated", false, true, saved)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Event>>> searchEvents(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Event> events = eventService.searchEvents(
                category != null ? Category.valueOf(category.toUpperCase()) : null,
                minPrice,
                maxPrice,
                startDate != null ? LocalDateTime.parse(startDate) : null,
                endDate != null ? LocalDateTime.parse(endDate) : null,
                page,
                size
        );

        return ResponseEntity.ok(new ApiResponse<>("Events retrieved successfully", false, true, events));
    }

    // ================= GET ALL EVENT =================
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<Page<Event>>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Event> events = eventService.getAllEvents(page, size);
        return ResponseEntity.ok(new ApiResponse<>("Events retrieved successfully", false, true, events));
    }

    // ================= GET ALL EVENT CALENDER =================
    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<List<Event>>> getCalendarEvents() {

        List<Event> events = eventService.getAllEvents(0, 1000).getContent();

        return ResponseEntity.ok(
                new ApiResponse<>("Calendar events", false, true, events)
        );
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ORGANIZER','ADMIN')")
    public ResponseEntity<ApiResponse<List<Event>>> getMyEvents() {

        User user = getCurrentUser();

        List<Event> events = eventService.getByOrganizerId(user.getId());

        return ResponseEntity.ok(
                new ApiResponse<>("My events", false, true, events)
        );
    }



}
