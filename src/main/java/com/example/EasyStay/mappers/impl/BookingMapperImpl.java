package com.example.EasyStay.mappers.impl;

import com.example.EasyStay.dtos.BookingDto;
import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements Mapper<BookingEntity, BookingDto> {

    private ModelMapper modelMapper;

    public BookingMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingDto mapTo(BookingEntity bookingEntity) {
        return modelMapper.map(bookingEntity, BookingDto.class);
    }

    @Override
    public BookingEntity mapFrom(BookingDto bookingDto) {
        return modelMapper.map(bookingDto, BookingEntity.class);
    }
}
