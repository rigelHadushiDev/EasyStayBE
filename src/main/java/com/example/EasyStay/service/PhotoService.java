package com.example.EasyStay.service;

import com.example.EasyStay.dtos.PhotoDto;
import com.example.EasyStay.entities.enums.PhotoType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {


    PhotoDto savePhoto(MultipartFile file, PhotoType type, Long referenceId ) throws IOException;
    List<PhotoDto> getPhotos(PhotoType type, Long referenceId);
    void deletePhoto(Long photoId) throws IOException;
}
