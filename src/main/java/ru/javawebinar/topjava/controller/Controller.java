package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.memorydao.memoryfactorydao.MemoryMealsFactoryDAOImpl;
import ru.javawebinar.topjava.dao.objectsdao.MealFactoryDAO;

public class Controller {

    public MealFactoryDAO getMealFactory() {
        return mealFactory;
    }

    private MealFactoryDAO mealFactory = new MemoryMealsFactoryDAOImpl();
}
