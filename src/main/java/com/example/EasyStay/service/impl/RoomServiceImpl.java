package com.example.EasyStay.service.impl;

import com.example.EasyStay.config.utils.Utils;
import com.example.EasyStay.dtos.RoomDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.entities.enums.RoomType;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.repository.HotelRepository;
import com.example.EasyStay.repository.RoomRepository;
import com.example.EasyStay.repository.specifications.BookingSpecifications;
import com.example.EasyStay.repository.specifications.RoomSpecifications;
import com.example.EasyStay.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl  implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final Utils utils;
    private final Mapper<RoomEntity, RoomDto> roomMapper;

    @Override
    public RoomEntity save(RoomEntity room) {

        Long currentUserId = utils.getCurrentUser().getUserId();

        HotelEntity existingHotel = hotelRepository.findHotelEntityByManager_UserId(currentUserId);
        if (existingHotel == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mustRegisterHotelFirst");
        }
        room.setHotel(existingHotel);
        return roomRepository.save(room);
    }

    @Override
    public RoomEntity edit(RoomEntity room) {
        RoomEntity existingRoom =  this.getRoomById(room.getRoomId());
        room.setHotel(existingRoom.getHotel());
        return roomRepository.save(room);
    }

    @Override
    public RoomDto deleteRoom(Long roomId) {
        RoomEntity existingRoom =  this.getRoomById(roomId);
        RoomDto roomDto = roomMapper.mapTo(existingRoom);
        roomRepository.delete(existingRoom);
        return roomDto;
    }

    @Override
    public RoomEntity getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "roomDoesntExist"));
    }

    @Override
    public Page<RoomEntity> searchRooms(List<RoomType> types, Double minPrice, Double maxPrice, Long hotelId,String sortBy, Pageable pageable) {
        Specification<RoomEntity> spec  = RoomSpecifications.buildSpecification(types, minPrice, maxPrice,hotelId, sortBy);
        return roomRepository.findAll(spec, pageable);
    }

    @Override
    public Page<RoomDto> searchAvailability(
            String city,
            String country,
            Integer guests,
            LocalDate checkIn,
            LocalDate checkOut,
            Pageable pageable
    ) {
        Page<RoomEntity> available = roomRepository.findAvailable(
                city, country, guests, checkIn, checkOut, pageable
        );
        return available.map(roomMapper::mapTo);
    }

}
