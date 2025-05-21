package com.example.EasyStay.repository;

import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long>, JpaSpecificationExecutor<BookingEntity> {


    List<BookingEntity> findByRoom_RoomIdAndIsCancelledFalseAndReservedFromLessThanEqualAndReservedToGreaterThanEqual(
            Long room_roomId, LocalDate reservedFrom, LocalDate reservedTo);

    long countByIsCancelledFalse();

    List<BookingEntity> findByIsCancelledFalse();
}