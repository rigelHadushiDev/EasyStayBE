package com.example.EasyStay.controller;

import com.example.EasyStay.dtos.RoomDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.entities.enums.RoomType;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.service.RoomService;
import com.example.EasyStay.validation.OnEditRoom;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/room")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final Mapper<RoomEntity, RoomDto> roomMapper;


    @PostMapping()
    public ResponseEntity<RoomDto> save(@Valid @RequestBody RoomDto roomDto) {
        RoomEntity roomEntity = roomMapper.mapFrom(roomDto);
        RoomEntity createdRoom = roomService.save(roomEntity);
        return new ResponseEntity<>( roomMapper.mapTo(createdRoom), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<RoomDto> deleteRoom(@RequestParam("roomId") Long roomId) {
        RoomDto deletedRoom = roomService.deleteRoom(roomId);
        return new ResponseEntity<>( deletedRoom, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<RoomDto> edit(@Validated(OnEditRoom.class) @RequestBody RoomDto roomDto) {
        RoomEntity roomEntity = roomMapper.mapFrom(roomDto);
        RoomEntity editedRoom = roomService.edit(roomEntity);
        return new ResponseEntity<>( roomMapper.mapTo(editedRoom), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<RoomDto> getRoomById(@RequestParam("roomId") Long roomId) {
        RoomEntity roomEntity = roomService.getRoomById(roomId);
        return new ResponseEntity<>(roomMapper.mapTo(roomEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<RoomDto>> searchRooms(
            @RequestParam(required = false) List<RoomType> types,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "newestFirst") String sortBy,
            Pageable pageable
    ) {
        Page<RoomEntity> rooms = roomService.searchRooms(types, minPrice, maxPrice, sortBy, pageable);
        Page<RoomDto> dtoPage = rooms.map(roomMapper::mapTo);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/search-availability")
    public ResponseEntity<Page<RoomDto>> searchAvailability(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer guests,
            @RequestParam String checkIn,
            @RequestParam String checkOut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
        LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);

        Pageable pageable = PageRequest.of(page, size);
        Page<RoomEntity> availableRooms = roomService.searchAvailability(city, country, guests, checkInDate, checkOutDate, pageable);
        Page<RoomDto> dtoPage = availableRooms.map(roomMapper::mapTo);
        return ResponseEntity.ok(dtoPage);
    }

}