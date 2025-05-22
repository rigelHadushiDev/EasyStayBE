package com.example.EasyStay.repository.specifications;

import com.example.EasyStay.entities.HotelEntity;
import org.springframework.data.jpa.domain.Specification;

public class HotelSpecifications {

    public static Specification<HotelEntity> hasManagerUsername(String username) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("manager").get("username")),
                        username.toLowerCase()
                );
    }

    public static Specification<HotelEntity> hasCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city != null && !city.isEmpty()) {
                return criteriaBuilder.equal(criteriaBuilder.lower(root.get("city")), city.toLowerCase());
            }
            return null;
        };
    }

    public static Specification<HotelEntity> hasCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country != null && !country.isEmpty()) {
                return criteriaBuilder.equal(criteriaBuilder.lower(root.get("country")), country.toLowerCase());
            }
            return null;
        };
    }

    public static Specification<HotelEntity> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null && !name.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<HotelEntity> buildSpecification(
            String username,
            String city,
            String country,
            String name
    ) {
        Specification<HotelEntity> spec = Specification.where(null);

        if (username != null && !username.isEmpty()) {
            spec = spec.and(hasManagerUsername(username));
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
