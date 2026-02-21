package com.himaja.blackrock.savings_api.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static long toMillis(String date) {
        return LocalDateTime.parse(date, FORMATTER)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}