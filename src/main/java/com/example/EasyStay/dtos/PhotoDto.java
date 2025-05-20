package com.example.EasyStay.dtos;


import com.example.EasyStay.entities.enums.PhotoType;
import com.example.EasyStay.validation.OnEditHotel;
import com.example.EasyStay.validation.OnEditPhoto;
import com.example.EasyStay.validation.OnEditRoom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDto {

    @NotNull(groups = {OnEditPhoto.class}, message = "PhotoId is required")
    private Long photoId;

    @NotNull(groups = {OnEditPhoto.class, Default.class},message = "Room type is required")
    private PhotoType type;

    @NotNull(groups = {OnEditPhoto.class, Default.class},message = "Room type is required")
    private Long referenceId;

    private String mediaName;

    private String url;
}