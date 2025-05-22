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
            String username,
            Long hotelId,
            Boolean isCancelled,
            Pageable pageable
    );

    BookingDto getById(Long bookingId);

    BookingStatsResponse getBookingStats();

}
