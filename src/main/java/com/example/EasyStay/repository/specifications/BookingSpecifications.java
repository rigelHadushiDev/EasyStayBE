package com.example.EasyStay.repository.specifications;

import com.example.EasyStay.entities.BookingEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookingSpecifications {

    public static Specification<BookingEntity> hasUsername(String username) {
        return (root, query, cb) -> {
            if (username != null && !username.isEmpty()) {
                return cb.equal(root.get("user").get("username"), username);
            }
            return null;
        };
    }

    public static Specification<BookingEntity> hasHotelId(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId != null) {
                return cb.equal(root.get("hotel").get("hotelId"), hotelId);
            }
            return null;
        };
    }


    public static Specification<BookingEntity> hasCancelledStatus(Boolean isCancelled) {
        return (root, query, cb) -> {
            if (isCancelled != null) {
                return cb.equal(root.get("isCancelled"), isCancelled);
            }
            return null;
        };
    }

    public static Specification<BookingEntity> buildSpecification(
            String username,
            Long hotelId,
            Boolean isCancelled
    ) {
        Specification<BookingEntity> spec = Specification.where(null);

        spec = spec.and(hasUsername(username))
                .and(hasHotelId(hotelId))
                .and(hasCancelledStatus(isCancelled));

        return spec;
    }
}