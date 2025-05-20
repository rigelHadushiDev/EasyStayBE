package com.example.EasyStay.entities;

import com.example.EasyStay.entities.enums.PhotoType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photoId")
    private Long photoId;

    @Enumerated(EnumType.STRING)
    private PhotoType type;

    private Long referenceId;

    private String mediaName;
}