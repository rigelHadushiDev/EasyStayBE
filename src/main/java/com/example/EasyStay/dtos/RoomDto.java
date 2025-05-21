package com.example.EasyStay.dtos;

import com.example.EasyStay.entities.enums.Amenity;
import com.example.EasyStay.entities.enums.RoomType;
import com.example.EasyStay.validation.OnEditRoom;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {

    @NotNull(groups = {OnEditRoom.class}, message = "RoomId is required")
    private Long roomId;

    @NotBlank(groups = {OnEditRoom.class, Default.class},message = "Room number is required")
    private String roomNumber;

    @NotNull(groups = {OnEditRoom.class, Default.class},message = "Room type is required")
    private RoomType type;

    @NotNull(groups = {OnEditRoom.class, Default.class},message = "Price per night is required")
    @DecimalMin(value = "0.0",  message = "Price must be zero or positive")
    private Double pricePerNight;

    @NotNull(groups = {OnEditRoom.class, Default.class},message = "Max guests is required")
    @Min(value = 1, message = "There must be at least one guest")
    private Integer maxGuests;

    private Set<Amenity> amenities;

    private String name;
    private String roadName;
    private String city;
    private String country;
    private Long hotelId;


}
