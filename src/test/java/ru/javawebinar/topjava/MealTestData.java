package ru.javawebinar.topjava;

import lombok.Getter;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public enum MealTestData {

    MEAL_1(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 100000),
    MEAL_2(100003, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, 100000),
    MEAL_3(100004, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, 100000),
    MEAL_4(100005, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, 100000),
    MEAL_5(100006, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, 100000),
    MEAL_6(100007, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 100000);

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

    public static boolean contains(Meal o) {
        int lenght = MealTestData.values().length;

        for (int i = 1; i <= lenght; i++) {
            if (o.toString().equals(MealTestData.valueOf(String.format("MEAL_%s", i)).toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAll(List<Meal> objects) {
        for (Meal o : objects) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", userid=" + userId +
                '}';
    }
}
