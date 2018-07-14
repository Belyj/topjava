package ru.javawebinar.topjava.service;

import lombok.Getter;
import java.time.LocalDateTime;
import java.time.Month;

public enum MealTestData {

    MEAL_1(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 100000),
    MEAL_2(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 100000),
    MEAL_3(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 100000),
    MEAL_4(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 100000),
    MEAL_5(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 100000),
    MEAL_6(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 100000);

    @Getter
    private int id;
    @Getter
    private LocalDateTime dateTime;
    @Getter
    private String description;
    @Getter
    private int calories;
    @Getter
    private int userId;

    MealTestData(int id, LocalDateTime dateTime, String description, int calories, int userId) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.userId = userId;
    }
}
