package com.example.EasyStay.config.utils;


import com.example.EasyStay.entities.UserEntity;
import com.example.EasyStay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class Utils {

    private final UserRepository userRepository;

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userNotFound"));
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public void assertCurrentUserOwns(Long entityOwnerId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(entityOwnerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "notOwnerOfResource");
        }
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}