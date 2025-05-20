package com.example.EasyStay.service.impl;

import com.example.EasyStay.dtos.PhotoDto;
import com.example.EasyStay.entities.PhotoEntity;
import com.example.EasyStay.entities.enums.PhotoType;
import com.example.EasyStay.mappers.Mapper;
import com.example.EasyStay.repository.PhotoRepository;
import com.example.EasyStay.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final Mapper<PhotoEntity, PhotoDto> photoMapper;

    @Override
    public PhotoDto savePhoto(MultipartFile file, PhotoType type, Long referenceId ) throws IOException {

        String baseDir = System.getProperty("user.dir");

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String newFileName = UUID.randomUUID() + extension;

        Path uploadDir = Paths.get(baseDir, "upload", type.name().toLowerCase(), referenceId.toString());
        Files.createDirectories(uploadDir);

        Path filePath = uploadDir.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setType(type);
        photoEntity.setReferenceId(referenceId);
        photoEntity.setMediaName(newFileName);

        PhotoEntity savedPhoto = photoRepository.save(photoEntity);

        return photoMapper.mapTo(savedPhoto);
    }

    @Override
    public List<PhotoDto> getPhotos(PhotoType type, Long referenceId) {
        List<PhotoEntity> photos = photoRepository.findByTypeAndReferenceId(type, referenceId);
        return photos.stream().map(photoMapper::mapTo).collect(Collectors.toList());
    }
    @Override
    public void deletePhoto(Long photoId) throws IOException {
        PhotoEntity photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found"));

        String baseDir = System.getProperty("user.dir");
        Path filePath = Paths.get(baseDir, "upload",
                photo.getType().name().toLowerCase(),
                photo.getReferenceId().toString(),
                photo.getMediaName());


        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        photoRepository.delete(photo);
    }

}
