package com.example.EasyStay.service;

import com.example.EasyStay.dtos.RoomDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    RoomEntity save(RoomEntity room);
    RoomEntity edit(RoomEntity room);
    RoomDto deleteRoom(Long roomId);
    RoomEntity getRoomById(Long roomId);
    Page<RoomEntity> searchRooms(List<RoomType> types, Double minPrice, Double maxPrice, String sortBy, Pageable pageable);
    Page<RoomEntity> searchAvailability(
            String city,
            String country,
            Integer guests,
            LocalDate checkIn,
            LocalDate checkOut,
            Pageable pageable);
}
