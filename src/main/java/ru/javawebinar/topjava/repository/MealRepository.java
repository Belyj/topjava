package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);

    void delete(int id, User user);

    Meal get(int id, User user);

    Collection<Meal> getAll(User user);
}
