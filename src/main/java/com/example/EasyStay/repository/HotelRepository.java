package com.example.EasyStay.repository;

import com.example.EasyStay.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, Long> , JpaSpecificationExecutor<HotelEntity> {
     HotelEntity findHotelEntityByManager_UserId( Long userId);
}