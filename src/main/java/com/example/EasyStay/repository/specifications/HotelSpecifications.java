package com.example.EasyStay.repository.specifications;

import com.example.EasyStay.entities.HotelEntity;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecifications {

    public static Specification<HotelEntity> hasManagerUserId(Long userId) {
        return (root, query, criteriaBuilder) -> {
            if (userId != null) {
                return criteriaBuilder.equal(root.get("manager").get("userId"), userId);
            }
            return null;
        };
    }

    public static Specification<HotelEntity> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city != null && !city.isEmpty()) {
                return criteriaBuilder.equal(root.get("city"), city);
            }
            return null;
        };
    }

    public static Specification<HotelEntity> hasCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country != null && !country.isEmpty()) {
                return criteriaBuilder.equal(root.get("country"), country);
            }
            return null;
        };
    }

    public static Specification<HotelEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null && !name.isEmpty()) {
                return criteriaBuilder.equal(root.get("name"), name);
            }
            return null;
        };
    }

    public static Specification<HotelEntity> buildSpecification(
            Long userId,
            String city,
            String country,
            String name
    ) {
        Specification<HotelEntity> spec = Specification.where(null);

        if (userId != null) {
            spec = spec.and(hasManagerUserId(userId));
        }
        if (city != null && !city.isEmpty()) {
            spec = spec.and(hasCity(city));
        }
        if (country != null && !country.isEmpty()) {
            spec = spec.and(hasCountry(country));
        }
        if (name != null && !name.isEmpty()) {
            spec = spec.and(hasName(name));
        }

        return spec;
    }
}