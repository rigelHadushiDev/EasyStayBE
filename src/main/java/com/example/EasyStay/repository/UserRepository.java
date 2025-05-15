package com.example.EasyStay.repository;

import com.example.EasyStay.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findUserByUserId(Long userId);
    Optional<UserEntity> findByUsername(String username);
    Page<UserEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);


}
