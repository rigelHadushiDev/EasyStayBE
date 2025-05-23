package com.example.EasyStay.service;

import com.example.EasyStay.entities.HotelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelService {
    HotelEntity save(HotelEntity hotel);
    HotelEntity edit(HotelEntity hotel);
    HotelEntity deleteHotel(Long hotelId);
    HotelEntity getHotelByHotelId(Long hotelId);
    Page<HotelEntity> searchHotels(String managerUserName, String city, String country, String name, Pageable pageable);
}