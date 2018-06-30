package ru.javawebinar.topjava.dao.objectsdao;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface MealDAO extends ObjectDAO<Meal> {

    @Override
    List<Meal> getAll();
}
