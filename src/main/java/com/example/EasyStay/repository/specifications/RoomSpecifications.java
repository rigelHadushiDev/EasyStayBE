package com.example.EasyStay.repository.specifications;

import com.example.EasyStay.entities.BookingEntity;
import com.example.EasyStay.entities.HotelEntity;
import com.example.EasyStay.entities.RoomEntity;
import com.example.EasyStay.entities.enums.RoomType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomSpecifications {

    public static Specification<RoomEntity> hasTypeIn(List<RoomType> types) {
        return (root, query, cb) -> {
            if (types != null && !types.isEmpty()) {
                return root.get("type").in(types);
            }
            return null;
        };
    }

    public static Specification<RoomEntity> hasPriceBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min != null && max != null) {
                return cb.between(root.get("pricePerNight"), min, max);
            } else if (min != null) {
                return cb.greaterThanOrEqualTo(root.get("pricePerNight"), min);
            } else if (max != null) {
                return cb.lessThanOrEqualTo(root.get("pricePerNight"), max);
            }
            return null;
        };
    }

    public static Specification<RoomEntity> applySorting(String sortBy) {
        return (root, query, cb) -> {
            List<Order> orders = new ArrayList<>();

            if ("priceLowToHigh".equalsIgnoreCase(sortBy)) {
                orders.add(cb.asc(root.get("pricePerNight")));
            } else if ("priceHighToLow".equalsIgnoreCase(sortBy)) {
                orders.add(cb.desc(root.get("pricePerNight")));
            } else if ("newestFirst".equalsIgnoreCase(sortBy)) {
                orders.add(cb.desc(root.get("roomId")));
            }

            if (!orders.isEmpty()) {
                query.orderBy(orders);
            }
            return cb.conjunction();
        };
    }

    public static Specification<RoomEntity> buildSpecification(
            List<RoomType> types,
            Double minPrice,
            Double maxPrice,
            String sortBy
    ) {
        Specification<RoomEntity> spec = Specification.where(null);

        if (types != null && !types.isEmpty()) {
            spec = spec.and(hasTypeIn(types));
        }
        if (minPrice != null || maxPrice != null) {
            spec = spec.and(hasPriceBetween(minPrice, maxPrice));
        }

        spec = spec.and(applySorting(sortBy));

        return spec;
    }


    public static Specification<RoomEntity> availableRooms(
            String city,
            String country,
            Integer guests,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        return (root, query, cb) -> {

            Join<RoomEntity, HotelEntity> hotel = root.join("hotel", JoinType.INNER);

            Predicate predicate = cb.conjunction();

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
                        cb.greaterThanOrEqualTo(root.get("maxGuests"), guests));
            }

            Subquery<Long> bookedRooms = query.subquery(Long.class);
            Root<BookingEntity> booking = bookedRooms.from(BookingEntity.class);
            bookedRooms.select(booking.get("roomId").get("roomId"));

            Predicate roomMatch = cb.equal(booking.get("roomId"), root);
            Predicate notCancelled = cb.isFalse(booking.get("isCancelled"));
            Predicate overlap = cb.and(
                    cb.lessThanOrEqualTo(booking.get("reservedFrom"), checkOut),
                    cb.greaterThanOrEqualTo(booking.get("reservedTo"), checkIn)
            );

            bookedRooms.where(cb.and(roomMatch, notCancelled, overlap));

            predicate = cb.and(predicate, cb.not(cb.exists(bookedRooms)));

            return predicate;
        };
    }
}
