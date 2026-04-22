package com.company.ticket_booking_backend.controller;

import com.company.ticket_booking_backend.model.ApiResponse;
import com.company.ticket_booking_backend.model.Category;
import com.company.ticket_booking_backend.model.Event;
import com.company.ticket_booking_backend.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private ObjectMapper objectMapper;
    private final EventService eventService;

    public EventController(EventService eventService , ObjectMapper objectMapper) {
        this.eventService = eventService;
        this.objectMapper = objectMapper;
    }

    // ================= CREATE EVENT (MULTI IMAGE) =================
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Event>> createEvent(@RequestPart("event") String eventJson , @RequestPart(value="images", required = false) List<MultipartFile> images) throws JsonProcessingException {

        Event event = objectMapper.readValue(eventJson, Event.class);

        Event newEvent = eventService.createEvent(event, images);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Event created successfully", false, true, newEvent));


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
    public ResponseEntity<ApiResponse<Void>> deleteEvent(@PathVariable("id") String id){
        System.out.println();
        if(id==null){
            return new ResponseEntity<>(new ApiResponse<>("Event not found", false, false, null), HttpStatus.NOT_FOUND);
        }
        Event event = eventService.getEventById(id);
        if(event==null){
            return new ResponseEntity<>(new ApiResponse<>("Event not found", false, false, null), HttpStatus.NOT_FOUND);

        }
        eventService.deleteEvent(id);
        return ResponseEntity.ok(new ApiResponse<>("Event deleted successfully", false, true, null));
    }

    // ================= EVENT ACTIVE /INACTIVE =================
    @PatchMapping("/toggle-active/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Void>> toggleEventActive(@PathVariable("id") String id){
        if(id==null){
            return new ResponseEntity<>(new ApiResponse<>("Event not found", false, false, null), HttpStatus.NOT_FOUND);

        }
        Event event = eventService.getEventById(id);
        if(event==null){
            return new ResponseEntity<>(new ApiResponse<>("Event not found", false, false, null), HttpStatus.NOT_FOUND);

        }

        eventService.toggleActive(id);
        return ResponseEntity.ok(new ApiResponse<>("Event updated successfully", false, true, null));
    }

    // ================= EVENT ACTIVE /INACTIVE =================
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable("id") String id , @RequestPart("event") String eventJson , @RequestPart(value="images", required = false) List<MultipartFile> images) throws JsonProcessingException {
        if (id == null) {
            return new ResponseEntity<>(new ApiResponse<>("Event not found", false, false, null), HttpStatus.NOT_FOUND);
        }
        Event event = objectMapper.readValue(eventJson, Event.class);

        Event updateEvent =eventService.updateEvent(id, event, images);
        return ResponseEntity.ok(new ApiResponse<>("Event updated successfully", false, true, updateEvent ));
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



}
