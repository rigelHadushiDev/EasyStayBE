package com.example.EasyStay.repository;

import com.example.EasyStay.entities.PhotoEntity;
import com.example.EasyStay.entities.enums.PhotoType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

    List<PhotoEntity> findByTypeAndReferenceId(PhotoType type, Long referenceId);
}