package com.example.EasyStay.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class BookingEntity {


    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @Column(name = "reserved_from", nullable = false)
    private LocalDate reservedFrom;

    @Column(name = "reserved_to", nullable = false)
    private LocalDate reservedTo;

    @Column(name = "is_cancelled")
    private Boolean isCancelled = false;

    @Column(name = "booking_ticket", unique = true, nullable = false, updatable = false)
    private String bookingTicket;

    @Column(name = "total_costs", nullable = false)
    private Double totalCosts;

}
