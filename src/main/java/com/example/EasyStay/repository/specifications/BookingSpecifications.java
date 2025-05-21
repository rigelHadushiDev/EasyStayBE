package com.example.EasyStay.repository.specifications;

import com.example.EasyStay.entities.BookingEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BookingSpecifications {

    public static Specification<BookingEntity> hasUserId(Long userId) {
        return (root, query, cb) -> {
            if (userId != null) {
                return cb.equal(root.get("user").get("userId"), userId);
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

    public static Specification<BookingEntity> reservedFromAfterOrEqual(LocalDate reservedFrom) {
        return (root, query, cb) -> {
            if (reservedFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("reservedFrom"), reservedFrom);
            }
            return null;
        };
    }

    public static Specification<BookingEntity> reservedToBeforeOrEqual(LocalDate reservedTo) {
        return (root, query, cb) -> {
            if (reservedTo != null) {
                return cb.lessThanOrEqualTo(root.get("reservedTo"), reservedTo);
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
            Long userId,
            Long hotelId,
            LocalDate reservedFrom,
            LocalDate reservedTo,
            Boolean isCancelled
    ) {
        Specification<BookingEntity> spec = Specification.where(null);

        spec = spec.and(hasUserId(userId))
                .and(hasHotelId(hotelId))
                .and(reservedFromAfterOrEqual(reservedFrom))
                .and(reservedToBeforeOrEqual(reservedTo))
                .and(hasCancelledStatus(isCancelled));

        return spec;
    }
}
