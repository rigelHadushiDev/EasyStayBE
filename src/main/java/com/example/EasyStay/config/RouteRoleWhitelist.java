package com.example.EasyStay.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteRoleWhitelist {

    public static final Map<String, List<String>> WHITELIST = new HashMap<>();

    static {
        // User routes
        WHITELIST.put("GET:/user/currentUser", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("GET:/user/listUsers", List.of("ADMIN", "RECRUITER"));
        WHITELIST.put("PATCH:/user/changePassw", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("PATCH:/user", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("DELETE:/user", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("DELETE:/user/{userId}", List.of("ADMIN"));
        WHITELIST.put("GET:/user/searchUserFullName", List.of("ADMIN", "MANAGER"));
        WHITELIST.put("POST:/user/create", List.of("ADMIN"));
        WHITELIST.put("GET:/user/getUser", List.of("ADMIN", "MANAGER"));

        // ROOM routes
        WHITELIST.put("POST:/room", List.of("MANAGER"));
        WHITELIST.put("PUT:/room", List.of("MANAGER"));
        WHITELIST.put("DELETE:/room", List.of("MANAGER", "ADMIN"));

        // Hotel routes
        WHITELIST.put("POST:/hotel", List.of("MANAGER"));
        WHITELIST.put("DELETE:/hotel", List.of("MANAGER", "ADMIN"));
        WHITELIST.put("PUT:/hotel", List.of("MANAGER"));

        // Photo routes
        WHITELIST.put("POST:/photo", List.of( "MANAGER"));
        WHITELIST.put("GET:/photo",List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("DELETE:/photo",List.of( "MANAGER"));

        // Reservations routes
        WHITELIST.put("POST:/booking", List.of("USER"));
        WHITELIST.put("GET:/booking/getById", List.of("USER", "ADMIN"));
        WHITELIST.put("PATCH:/booking/cancel", List.of("USER", "MANAGER"));
        WHITELIST.put("GET:/booking", List.of("USER", "MANAGER"));
        WHITELIST.put("GET:/booking/getStats", List.of("MANAGER"));




        // should be public

        // have query params
        WHITELIST.put("GET:/booking/search-availability", List.of("USER"));
        WHITELIST.put("GET:/room/getById", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("GET:/room", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("GET:/hotel/getById", List.of("ADMIN", "MANAGER", "USER"));
        WHITELIST.put("GET:/hotel", List.of("ADMIN", "MANAGER", "USER"));

    }
}
