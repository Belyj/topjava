package ru.javawebinar.topjava.dao.memorydao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class DataSourceInit {

    private static volatile DataSourceInit instance;

    public static DataSourceInit getInstance() {
        DataSourceInit localInstance = instance;
        if (localInstance == null) {
            synchronized (DataSourceInit.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DataSourceInit();
                }
            }
        }
        return localInstance;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    private List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
            );
}
