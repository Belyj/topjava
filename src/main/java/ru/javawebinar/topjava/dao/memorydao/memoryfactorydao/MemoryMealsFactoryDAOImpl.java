package ru.javawebinar.topjava.dao.memorydao.memoryfactorydao;

import ru.javawebinar.topjava.dao.memorydao.MemoryMealsDAOImpl;
import ru.javawebinar.topjava.dao.objectsdao.MealDAO;
import ru.javawebinar.topjava.dao.objectsdao.MealFactoryDAO;
import ru.javawebinar.topjava.dao.objectsdao.ObjectDAO;
import ru.javawebinar.topjava.model.Meal;

public class MemoryMealsFactoryDAOImpl implements MealFactoryDAO {

    @Override
    public ObjectDAO factoryMethod() {
        return MemoryMealsDAOImpl.getInstance();
    }
}
