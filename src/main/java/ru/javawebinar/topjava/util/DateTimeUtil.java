package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<MealWithExceed> isBetween(List<MealWithExceed> mealWithExceeds, String fromDate, String toDate, String fromTime, String toTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (!fromDate.isEmpty()) {
            mealWithExceeds = mealWithExceeds.stream().filter(x -> x.getDateTime().toLocalDate().compareTo(LocalDate.parse(fromDate, dateFormatter)) >= 0).collect(Collectors.toList());
        }

        if (!toDate.isEmpty()) {
            mealWithExceeds = mealWithExceeds.stream().filter(x -> x.getDateTime().toLocalDate().compareTo(LocalDate.parse(toDate, dateFormatter)) <= 0).collect(Collectors.toList());
        }

        if (!fromTime.isEmpty()) {
            mealWithExceeds = mealWithExceeds.stream().filter(x -> x.getDateTime().toLocalTime().compareTo(LocalTime.parse(fromTime, timeFormatter)) >= 0).collect(Collectors.toList());
        }

        if (!toTime.isEmpty()) {
            mealWithExceeds = mealWithExceeds.stream().filter(x -> x.getDateTime().toLocalTime().compareTo(LocalTime.parse(toTime, timeFormatter)) <= 0).collect(Collectors.toList());
        }
        return mealWithExceeds;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
