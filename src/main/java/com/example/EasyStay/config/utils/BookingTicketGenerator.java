package com.example.EasyStay.config.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.security.SecureRandom;

@Component
public class BookingTicketGenerator {

    private static final String PREFIX = "BK";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateBookingTicket() {
        String datePart = LocalDate.now().format(DATE_FORMAT);
        String randomPart = randomAlphanumeric();
        return PREFIX + "-" + datePart + "-" + randomPart;
    }

    private static String randomAlphanumeric() {
        StringBuilder sb = new StringBuilder(5);
        for(int i = 0; i < 5; i++) {
            int index = random.nextInt(ALPHANUM.length());
            sb.append(ALPHANUM.charAt(index));
        }
        return sb.toString();
    }
}
