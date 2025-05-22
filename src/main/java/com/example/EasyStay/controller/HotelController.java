package com.example.EasyStay.controller;

import com.example.EasyStay.dtos.HotelDto;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.service.HotelService;
import com.example.EasyStay.validation.OnEditHotel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hotel")
@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final Mapper<HotelEntity, HotelDto> hotelMapper;


    @PostMapping()
    public ResponseEntity<HotelDto> save(@Valid @RequestBody HotelDto hotelDto) {
        HotelEntity hotelEntity = hotelMapper.mapFrom(hotelDto);
        HotelEntity createdHotel = hotelService.save(hotelEntity);
        return new ResponseEntity<>( hotelMapper.mapTo(createdHotel), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<HotelDto> deleteHotel(@RequestParam("hotelId") Long hotelId) {
        HotelEntity deletedHotel = hotelService.deleteHotel(hotelId);
        return new ResponseEntity<>( hotelMapper.mapTo(deletedHotel), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<HotelDto> edit(@Validated(OnEditHotel.class) @RequestBody HotelDto hotelDto) {
        HotelEntity hotelEntity = hotelMapper.mapFrom(hotelDto);
        HotelEntity editedHotel = hotelService.edit(hotelEntity);
        return new ResponseEntity<>( hotelMapper.mapTo(editedHotel), HttpStatus.OK);
    }

    @GetMapping("/getById")
    public ResponseEntity<HotelDto> getHotel(@RequestParam("hotelId") Long hotelId) {
        HotelEntity hotelEntity = hotelService.getHotelByHotelId(hotelId);
        return new ResponseEntity<>(hotelMapper.mapTo(hotelEntity), HttpStatus.OK);
    }

    @GetMapping("/filterHotels")
    public ResponseEntity<Page<HotelDto>> getHotels(
            @RequestParam(required = false) String managerUserName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        Page<HotelEntity> hotels = hotelService.searchHotels(managerUserName, city, country, name, pageable);
        Page<HotelDto> dtoPage = hotels.map(hotelMapper::mapTo);
        return ResponseEntity.ok(dtoPage);
    }
}



