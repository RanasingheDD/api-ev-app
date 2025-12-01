package com.ev_booking_system.api.controller;

   
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ev_booking_system.api.dto.BookingDto;
import com.ev_booking_system.api.dto.BookingQuoteDto;
import com.ev_booking_system.api.model.BookingModel;
import com.ev_booking_system.api.service.BookingService;
import org.springframework.http.ResponseEntity;
import com.ev_booking_system.api.model.BookingStatus;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Autowired
    private BookingService service;

    @PostMapping("/quote")
    public BookingQuoteDto getQuote(@RequestBody Map<String, Object> body) {

        return service.getQuote(
                (String) body.get("chargerId"),
                (String) body.get("stationId"),
                Instant.parse((String) body.get("startAt")),
                Instant.parse((String) body.get("endAt"))
        );
    }

    @PostMapping
    public BookingModel createBooking(@RequestBody BookingModel booking,@RequestHeader("Authorization") String token) {
        return service.createBooking(booking,token);
    }

    @GetMapping("/{id}")
    public BookingModel getBooking(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
   
   /*  public ResponseEntity<?> getUserBookings(
            @RequestParam(required = false) String status,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(
                Map.of("bookings", bookingService.getUserBookings(token, status))
        );
    }*/
    
    @PostMapping("/{id}/cancel")
    public BookingModel cancel(@PathVariable String id, @RequestBody Map<String, String> body) {
        return service.cancelBooking(id, body.get("reason"));
    }

    @PostMapping("/check-availability")
    public Map<String, Object> checkAvail(@RequestBody Map<String, Object> body) {

        boolean available = service.checkAvailability(
                (String) body.get("chargerId"),
                Instant.parse((String) body.get("startAt")),
                Instant.parse((String) body.get("endAt"))
        );

        return Map.of("available", available);
    }
}

 