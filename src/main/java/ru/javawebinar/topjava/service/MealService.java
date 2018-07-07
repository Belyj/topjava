package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {

    void save(Meal meal);

    Meal create(Meal meal);

    void update(Meal meal);

    void delete(int id) throws NotFoundException;

    List<Meal> getAll();

}