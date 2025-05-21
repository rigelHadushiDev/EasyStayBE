package com.example.EasyStay.repository.specifications;

import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.RoomEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import com.example.EasyStay.entities.HotelEntity;
import java.time.LocalDate;

public class BookingSpecifications {

    public static Specification<BookingEntity> hasUserId(Long userId) {
        return (root, query, cb) -> {
            if (userId != null) {
                return cb.equal(root.get("userId").get("userId"), userId);
            }
            return null;
        };
    }

    public static Specification<BookingEntity> hasHotelId(Long hotelId) {
        return (root, query, cb) -> {
            if (hotelId != null) {
                return cb.equal(root.get("hotelId").get("hotelId"), hotelId);
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

    public static Specification<BookingEntity> searchAvailability(
            String city,
            String country,
            Integer guests,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        return (root, query, cb) -> {
            Join<BookingEntity, HotelEntity> hotel = root.join("hotelId", JoinType.INNER);
            Join<BookingEntity, RoomEntity> room = root.join("roomId", JoinType.INNER);

            Predicate predicate = cb.conjunction();

            predicate = cb.and(predicate,
                    cb.lessThanOrEqualTo(root.get("reservedFrom"), checkOut),
                    cb.greaterThanOrEqualTo(root.get("reservedTo"), checkIn));

            predicate = cb.and(predicate, cb.isFalse(root.get("isCancelled")));

            if (city != null && !city.trim().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(hotel.get("city")), city.trim().toLowerCase()));
            }

            if (country != null && !country.trim().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(cb.lower(hotel.get("country")), country.trim().toLowerCase()));
            }

            if (guests != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(room.get("maxGuests"), guests));
            }

            return predicate;
        };
    }

}
