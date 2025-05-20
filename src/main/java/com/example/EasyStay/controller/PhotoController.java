package com.example.EasyStay.controller;

import com.example.EasyStay.dtos.PhotoDto;
import com.example.EasyStay.entities.enums.PhotoType;
import com.example.EasyStay.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/photo")
@RestController
@RequiredArgsConstructor
public class PhotoController {


    private final PhotoService photoService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoDto> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") PhotoType type,
            @RequestParam("id") Long referenceId) throws IOException {

        PhotoDto saved = photoService.savePhoto(file, type, referenceId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<PhotoDto>> getPhotos(
            @RequestParam("type") PhotoType type,
            @RequestParam("referenceId") Long referenceId) {

        List<PhotoDto> photos = photoService.getPhotos(type, referenceId);
        return ResponseEntity.ok(photos);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePhoto(@RequestParam("photoId") Long photoId) throws IOException {
        photoService.deletePhoto(photoId);
        return ResponseEntity.noContent().build();
    }
}
