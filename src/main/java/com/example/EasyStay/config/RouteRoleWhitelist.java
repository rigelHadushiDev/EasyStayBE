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
    }
}
