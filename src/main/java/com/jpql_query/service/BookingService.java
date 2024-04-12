package com.jpql_query.service;

import com.jpql_query.entity.Booking;
import com.jpql_query.entity.Property;
import com.jpql_query.entity.PropertyUser;
import com.jpql_query.exception.BookingException;
import com.jpql_query.repository.BookingRepository;
import com.jpql_query.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class BookingService {
    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;

    public BookingService(BookingRepository bookingRepository, PropertyRepository propertyRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
    }

    public Booking addBooking(Booking booking, PropertyUser propertyUser) {
        Optional<Property> byId = propertyRepository.findById(booking.getProperty().getId());
        Property property = byId.get();
        Booking check = bookingRepository.findByCheckInAndCheckOut(booking.getCheckIn(), booking.getCheckOut(), property);
        if (check != null) {
            throw new BookingException("Booking is not allowed");

        }
        booking.setProperty(property);
        long totalNights = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());

        // Calculate the total price based on nightly price and total nights
        Integer nightlyPrice = booking.getProperty().getNightlyPrice();
        int totalPrice = nightlyPrice * Math.toIntExact(totalNights);
        booking.setTotalPrice(totalPrice);

        booking.setPropertyUser(propertyUser);
        return bookingRepository.save(booking);
    }
}
