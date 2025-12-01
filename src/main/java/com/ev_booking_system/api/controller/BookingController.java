package com.ev_booking_system.api.controller;

   
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.ev_booking_system.api.dto.BookingDto;
import com.ev_booking_system.api.dto.BookingQuoteDto;
import com.ev_booking_system.api.model.BookingModel;
import com.ev_booking_system.api.service.BookingService;

import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

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
    public Map<String, Object> getBookings(
            @RequestParam(required = false) String status,
            @RequestHeader("userId") String userId
    ) {
        List<BookingModel> list = service.getUserBookings(userId, status);

        return Map.of("bookings", list);
    }

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

 