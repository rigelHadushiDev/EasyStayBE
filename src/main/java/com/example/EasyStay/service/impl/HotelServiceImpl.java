package com.example.EasyStay.service.impl;

import com.example.EasyStay.config.utils.Utils;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.entities.enums.Role;
import com.example.EasyStay.repository.HotelRepository;
import com.example.EasyStay.repository.specifications.HotelSpecifications;
import com.example.EasyStay.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final Utils utils;


    @Override
    public HotelEntity save(HotelEntity hotel) {

        Long currentUserId = utils.getCurrentUser().getUserId();

        HotelEntity existingHotel = hotelRepository.findHotelEntityByManager_UserId(currentUserId);
        if (existingHotel != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cantRegisterMoreThanOneHotel");
        }
        return hotelRepository.save(hotel);
    }

    @Override
    public HotelEntity edit(HotelEntity hotel) {
        HotelEntity existingHotel = this.getHotelByHotelId(hotel.getHotelId());
        hotel.setManager(existingHotel.getManager());
        return hotelRepository.save(hotel);
    }

    @Override
    public HotelEntity deleteHotel(Long hotelId) {
        HotelEntity existingHotel =  this.getHotelByHotelId(hotelId);
        hotelRepository.delete(existingHotel);
        return existingHotel;
    }

    @Override
    public HotelEntity getHotelByHotelId(Long hotelId) {

        return hotelRepository.findById(hotelId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "hotelDoesntExist"));
    }

    @Override
    public Page<HotelEntity> searchHotels(Long managerUserId, String city, String country, String name, Pageable pageable) {
        Long currentUserId = utils.getCurrentUser().getUserId();
        Role currentUserRole = utils.getCurrentUser().getRole();
        Specification<HotelEntity> spec;

        if (managerUserId != null && managerUserId.equals(currentUserId)) {
            spec = HotelSpecifications.buildSpecification(managerUserId, city, country, name);
        } else {
            spec = HotelSpecifications.buildSpecification(null, city, country, name);
        }
        return hotelRepository.findAll(spec, pageable);
    }
}
