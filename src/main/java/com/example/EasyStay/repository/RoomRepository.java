package com.example.EasyStay.repository;

import com.example.EasyStay.entities.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {

    Page<RoomEntity> findByHotel_HotelId(Long hotelId, Pageable pageable);
}