package com.example.EasyStay.service;


import com.example.EasyStay.dtos.BookingDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.responses.BookingStatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BookingService {

    BookingDto book( BookingDto bookingDto );
    BookingDto cancel(Long bookingId);
    Page<BookingDto> filterBookings(
            Long userId,
            Long hotelId,
            LocalDate reservedFrom,
            LocalDate reservedTo,
            Boolean isCancelled,
            Pageable pageable
    );

    BookingDto getById(Long bookingId);

    BookingStatsResponse getBookingStats();

    Page<BookingEntity> searchAvailability(
            String city,
            String country,
            Integer guests,
            LocalDate checkIn,
            LocalDate checkOut,
            Pageable pageable);
}
