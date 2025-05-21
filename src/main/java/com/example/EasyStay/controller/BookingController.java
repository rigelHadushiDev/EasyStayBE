package com.example.EasyStay.controller;

import com.example.EasyStay.dtos.BookingDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.responses.BookingStatsResponse;
import com.example.EasyStay.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequestMapping("/booking")
@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    private ResponseEntity<BookingDto> book(@Valid @RequestBody BookingDto bookingDto) {
        BookingDto savedBooking = bookingService.book(bookingDto);
        return new ResponseEntity<>(savedBooking, HttpStatus.OK);
    }

    @GetMapping("/getById")
    private ResponseEntity<BookingDto> getById(@RequestParam Long bookingId) {
        BookingDto booking = bookingService.getById(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PatchMapping("/cancel")
    private ResponseEntity<BookingDto> cancel(@RequestParam Long bookingId ) {
        BookingDto canceledBooking = bookingService.cancel(bookingId);
        return new ResponseEntity<>(canceledBooking, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<BookingDto>> filterBookings(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long hotelId,
            @RequestParam(required = false)
            String reservedFrom,
            @RequestParam(required = false)
            String reservedTo,
            @RequestParam(required = false) Boolean isCancelled,
            Pageable pageable
    ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate reservedFromDate = LocalDate.parse(reservedFrom, formatter);
        LocalDate reservedToDate = LocalDate.parse(reservedTo, formatter);

        Page<BookingDto> bookings = bookingService.filterBookings(userId, hotelId, reservedFromDate, reservedToDate, isCancelled, pageable);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/getStats")
    public ResponseEntity<BookingStatsResponse> getStats() {
        BookingStatsResponse stats = bookingService.getBookingStats();
        return ResponseEntity.ok(stats);
    }







}
