package ru.application.homemedkit.helpers;

import static ru.application.homemedkit.helpers.ConstantsHelperKt.BLANK;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DateHelper {
    public static final ZoneOffset ZONE = ZoneId.systemDefault().getRules().getOffset(Instant.now());
    public static final DateTimeFormatter FORMAT_L = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
    public static final DateTimeFormatter FORMAT_S = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter FORMAT_D_H = DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm");
    public static final DateTimeFormatter FORMAT_D_M_Y = DateTimeFormatter.ofPattern("d MMMM yyyy");
    public static final DateTimeFormatter FORMAT_D_MM_Y = DateTimeFormatter.ofPattern("d MMM yyyy");
    public static final DateTimeFormatter FORMAT_D_M = DateTimeFormatter.ofPattern("d MMMM, E");
    public static final DateTimeFormatter FORMAT_H = DateTimeFormatter.ofPattern("H:mm");
    private static final DateTimeFormatter FORMAT_M_Y = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String toExpDate(long milli) {
        return milli > 0 ? getDateTime(milli).format(FORMAT_L) : BLANK;
    }

    public static String toExpDate(int month, int year) {
        return LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth()).format(FORMAT_L);
    }

    public static long toTimestamp(int month, int year) {
        return LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(),
                LocalTime.MAX.getHour(), LocalTime.MAX.getMinute()).toInstant(ZONE).toEpochMilli();
    }

    public static String inCard(long milli) {
        return milli == -1L ? BLANK : getDateTime(milli).format(FORMAT_M_Y);
    }

    public static String getPeriod(String dateS, String dateF) {
        LocalDate startD = LocalDate.parse(dateS, FORMAT_S);
        LocalDate finalD = LocalDate.parse(dateF, FORMAT_S);

        return String.valueOf(Duration.between(startD.atStartOfDay(), finalD.atStartOfDay()).toDays());
    }

    public static long lastDay(String finish) {
        LocalDate date = LocalDate.parse(finish, FORMAT_S);

        return LocalDateTime.of(date, LocalTime.now()).toInstant(ZONE).toEpochMilli();
    }

    public static long expirationCheckTime() {
        LocalDateTime unix = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));

        if (unix.toInstant(ZONE).toEpochMilli() < System.currentTimeMillis()) {
            unix = unix.plusDays(1);
        }

        return unix.toInstant(ZONE).toEpochMilli();
    }

    public static ZonedDateTime getDateTime(long milli) {
        return Instant.ofEpochMilli(milli).atZone(ZONE);
    }
}
