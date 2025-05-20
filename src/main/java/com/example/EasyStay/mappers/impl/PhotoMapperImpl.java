package com.example.EasyStay.mappers.impl;

import com.example.EasyStay.dtos.PhotoDto;
import com.example.EasyStay.entities.PhotoEntity;
import com.example.EasyStay.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapperImpl implements Mapper<PhotoEntity, PhotoDto> {
    private ModelMapper modelMapper;

    public PhotoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public PhotoDto mapTo(PhotoEntity photoEntity) {
        PhotoDto dto = modelMapper.map(photoEntity, PhotoDto.class);

        String serverPath = "http://localhost:8080";

        String url = serverPath + "/media/" +
                photoEntity.getType().name().toLowerCase() + "/" +
                photoEntity.getReferenceId() + "/" +
                photoEntity.getMediaName();

        dto.setUrl(url);
        return dto;
    }

    @Override
    public PhotoEntity mapFrom(PhotoDto photoDto) {
        return modelMapper.map(photoDto, PhotoEntity.class);
    }
}

