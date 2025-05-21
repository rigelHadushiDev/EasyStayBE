package com.example.EasyStay.config;

public class PublicRouteChecker {
    public static boolean isPublic(String path) {
        return path.startsWith("/auth") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-ui.html") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/v3/api-docs.yaml") ||
                path.startsWith("/webjars") ||
                path.startsWith("/media/") ||
                path.startsWith("/room/search-availability") ||
                path.startsWith("/room/getById") ||
                path.equals("/room") ||
                path.startsWith("/hotel/getById") ||
                path.equals("/hotel");
    }
}