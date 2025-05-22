package com.example.EasyStay.repository;

import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity> {


    List<BookingEntity> findByRoom_RoomIdAndIsCancelledFalseAndReservedFromLessThanEqualAndReservedToGreaterThanEqual(
            Long room_roomId, LocalDate reservedFrom, LocalDate reservedTo);


    @Query("""
    SELECT b
    FROM BookingEntity b
    JOIN b.hotel h
    JOIN h.manager m
    WHERE b.isCancelled = false
      AND m.userId = :managerUserId
""")
    List<BookingEntity> findAllNotCancelledByManager(@Param("managerUserId") Long managerUserId);

    @Query("""
    SELECT COUNT(b)
    FROM BookingEntity b
    JOIN b.hotel h
    JOIN h.manager m
    WHERE b.isCancelled = false
      AND m.userId = :managerUserId
""")
    long countNotCancelledByManager(@Param("managerUserId") Long managerUserId);
}