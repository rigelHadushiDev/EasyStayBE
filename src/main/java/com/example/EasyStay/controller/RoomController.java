package com.example.EasyStay.controller;

import com.example.EasyStay.dtos.RoomDto;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.service.RoomService;
import com.example.EasyStay.validation.OnEditRoom;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        RoomEntity deletedRoom = roomService.deleteRoom(roomId);
        return new ResponseEntity<>( roomMapper.mapTo(deletedRoom), HttpStatus.OK);
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

}