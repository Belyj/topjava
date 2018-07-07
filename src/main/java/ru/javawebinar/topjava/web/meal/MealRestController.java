package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.List;

public class MealRestController {
    private MealService service;

    public void save(Meal meal) {
        service.save(meal);
    }

    public Meal create(Meal meal, int userId) {
        return service.create(meal);
    }

    public void update(Meal meal, int userId) {
        service.update(meal);
    }

    public void delete(int mealId, int userId) {
        service.delete(mealId);
    }

    public List<Meal> getAll(int userId) {
        return service.getAll();
    }
}