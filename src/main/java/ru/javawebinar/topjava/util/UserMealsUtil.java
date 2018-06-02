package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {

    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                                                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                                                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                                                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                                                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                                                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        List<UserMeal> meals = new LinkedList<>();
        meals.addAll(mealList);
        List<UserMeal> dateMealList = new ArrayList<>();

        while (!meals.isEmpty()) {
            dateMealList.addAll(meals.stream().limit(3).collect(Collectors.toList()));

            dateMealList.stream().filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime)).
                    forEach(y -> userMealWithExceeds.add(new UserMealWithExceed(y.getDateTime(), y.getDescription(), y.getCalories(), caloriesPerDay >= calculateCallories(dateMealList))));

            meals.removeAll(dateMealList);
            dateMealList.clear();
        }
        return userMealWithExceeds;
    }

    private static int calculateCallories(List<UserMeal> dateMealList) {
        int i = 0;
        for (UserMeal u : dateMealList) {
            i = i + u.getCalories();
        }
        return i;
    }
}