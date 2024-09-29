package sn.ucad.mscustomerorder.helper.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static Date parseOrderDate(String dateString) {
        return Date.from(LocalDateTime.parse(dateString, formatter).toInstant(java.time.ZoneOffset.UTC));
    }

    public static String formatOrderDate(Date dateTime) {
        return formatter.format(dateTime.toInstant());
    }
}
