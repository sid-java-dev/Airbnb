package com.jpql_query.controller;

import com.jpql_query.entity.Booking;
import com.jpql_query.entity.PropertyUser;
import com.jpql_query.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking,
                                           @AuthenticationPrincipal PropertyUser propertyUser) {
        Booking addedBooking = bookingService.addBooking(booking,propertyUser);
        return new ResponseEntity<>(addedBooking, HttpStatus.CREATED);
    }
}
