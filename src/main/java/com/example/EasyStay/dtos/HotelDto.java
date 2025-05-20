package com.example.EasyStay.dtos;

import com.example.EasyStay.validation.OnEditHotel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelDto {

    @NotNull(groups = {OnEditHotel.class}, message = "HotelId is required")
    private Long hotelId;

    @NotBlank(groups = {OnEditHotel.class, Default.class}, message = "Name of the hotel is required")
    private String name;

    @NotBlank(groups = {OnEditHotel.class, Default.class}, message = "Description of the hotel is required")
    private String description;

    @NotBlank(groups = {OnEditHotel.class, Default.class}, message = "Road name is required.")
    private String roadName;

    @NotBlank(groups = {OnEditHotel.class, Default.class}, message = "City is required.")
    private String city;

    @NotBlank(groups = {OnEditHotel.class, Default.class}, message = "Country is required.")
    private String country;

    private LocalDateTime createdAt;
    private Long managerId;

}
