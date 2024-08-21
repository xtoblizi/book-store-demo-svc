package com.book.store.svc.commons.utils;

import com.book.store.svc.commons.exceptions.UtilException;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int HOUR_IN_SEC = 60 * 60;

    private DateTimeUtil() throws UtilException {
        throw new UtilException("Cannot construct create an instance of this util. Kindly use its behaviours directly");
    }

    public static ZoneId getDefaultZoneId() {
        return ZoneId.of("Africa/Lagos");
    }

    public static LocalDate parseLocalDate(String s) throws UtilException {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            throw new UtilException("Failed to format date to dd-MM-yyyy or dd/MM/yyyy ->" + s);
        }
    }

    public static LocalDate parseDate(String s) throws UtilException {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new UtilException("Failed to format date to yyyy-MM-dd ->" + s);
        }
    }

    public static LocalDate parseDate(String s, String format) throws UtilException {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            throw new UtilException("Failed to format date to " + format + " ->" + s);
        }
    }

    public static LocalDateTime parseDateTime(String s) throws UtilException {
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            throw new UtilException("Failed to format date to yyyy-MM-dd HH:mm ->" + s);
        }
    }

    public static LocalDateTime parseDateTime(String s, String format) throws UtilException {
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            throw new UtilException("Failed to format date to " + format + " ->" + s);
        }
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        return localDateTime.format(formatter);
    }

    public static String formatLocalDateForMssqlParam(LocalDateTime localDateTime, int signedGmtHours) {
        int hourOnGmt = signedGmtHours * (HOUR_IN_SEC);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant().plusSeconds(hourOnGmt);
        return instant.toString();
    }

    public static String formatLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.from(currentInstant());
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.ofInstant(currentInstant(), ZoneId.of("Africa/Lagos"));
    }

    private static Instant currentInstant() {
        return ZonedDateTime.now(ZoneId.of(getDefaultZoneId().toString())).toInstant();
    }
}
