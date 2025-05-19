package com.example.EasyStay.mappers.impl;

import com.example.EasyStay.dtos.HotelDto;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HotelMapperImpl implements Mapper<HotelEntity, HotelDto> {
    private ModelMapper modelMapper;

    public HotelMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HotelDto mapTo(HotelEntity hotelEntity) {
        return modelMapper.map(hotelEntity, HotelDto.class);
    }

    @Override
    public HotelEntity mapFrom(HotelDto hotelDto) {
        return modelMapper.map(hotelDto, HotelEntity.class);
    }
}
