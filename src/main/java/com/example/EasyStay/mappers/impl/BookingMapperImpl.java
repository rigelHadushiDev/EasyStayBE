package com.example.EasyStay.mappers.impl;

import com.example.EasyStay.dtos.BookingDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements Mapper<BookingEntity, BookingDto> {

    private final ModelMapper modelMapper;

    public BookingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.addMappings(new PropertyMap<BookingEntity, BookingDto>() {
            @Override
            protected void configure() {
                map().setRoomId(source.getRoom().getRoomId());
                map().setHotelId(source.getHotel().getHotelId());
                map().setUserId(source.getUser().getUserId());
            }
        });

        this.modelMapper.addMappings(new PropertyMap<BookingDto, BookingEntity>() {
            @Override
            protected void configure() {
                skip(destination.getRoom());
                skip(destination.getHotel());
                skip(destination.getUser());
            }
        });
    }

    @Override
    public BookingDto mapTo(BookingEntity bookingEntity) {
        return modelMapper.map(bookingEntity, BookingDto.class);
    }

    @Override
    public BookingEntity mapFrom(BookingDto bookingDto) {
        BookingEntity entity = modelMapper.map(bookingDto, BookingEntity.class);

        // Manually set nested entities from IDs since we skipped them in ModelMapper
        if (bookingDto.getRoomId() != null) {
            RoomEntity room = new RoomEntity();
            room.setRoomId(bookingDto.getRoomId());
            entity.setRoom(room);
        }
        if (bookingDto.getHotelId() != null) {
            HotelEntity hotel = new HotelEntity();
            hotel.setHotelId(bookingDto.getHotelId());
            entity.setHotel(hotel);
        }
        if (bookingDto.getUserId() != null) {
            UserEntity user = new UserEntity();
            user.setUserId(bookingDto.getUserId());
            entity.setUser(user);
        }

        return entity;
    }
}
