package com.example.EasyStay.mappers.impl;

import com.example.EasyStay.dtos.RoomDto;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoomMapperImpl implements Mapper<RoomEntity, RoomDto> {
    private ModelMapper modelMapper;

    public RoomMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RoomDto mapTo(RoomEntity roomEntity) {
        return modelMapper.map(roomEntity, RoomDto.class);
    }

    @Override
    public RoomEntity mapFrom(RoomDto roomDto) {
        return modelMapper.map(roomDto, RoomEntity.class);
    }
}
