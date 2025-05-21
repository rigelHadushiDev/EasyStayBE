package com.example.EasyStay.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    private Long bookingId;

    private Long hotelId;

    @NotNull( message = "Room Id is required")
    private Long roomId;

    @NotNull( message = "Date that is reserved from is required")
    private LocalDate reservedFrom;

    @NotNull(message = "Date that is reserved to is required")
    private LocalDate reservedTo;

    private Long userId;

    private Boolean isCancelled;

    private String bookingTicket;

    private Double totalCosts;

    private String username;

}
