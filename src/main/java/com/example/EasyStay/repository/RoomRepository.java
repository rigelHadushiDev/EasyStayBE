package com.example.EasyStay.repository;

import com.example.EasyStay.dtos.RoomDto;
import com.example.EasyStay.entities.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long>, JpaSpecificationExecutor<RoomEntity> {

    Page<RoomEntity> findByHotel_HotelId(Long hotelId, Pageable pageable);

    @Query("""
      SELECT r
      FROM   RoomEntity r
      JOIN   r.hotel h
      WHERE
        (:city    IS NULL OR LOWER(h.city)    = LOWER(:city))
        AND (:country IS NULL OR LOWER(h.country) = LOWER(:country))
        AND (:guests  IS NULL OR r.maxGuests       = :guests)
        AND NOT EXISTS (
          SELECT 1
          FROM   BookingEntity b
          WHERE  b.room         = r
            AND  b.isCancelled = FALSE
            AND  b.reservedFrom <= :checkOut
            AND  b.reservedTo   >= :checkIn
        )
    """)
    Page<RoomEntity> findAvailable(
            @Param("city")     String city,
            @Param("country")  String country,
            @Param("guests")   Integer guests,
            @Param("checkIn")  LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            Pageable pageable
    );
}