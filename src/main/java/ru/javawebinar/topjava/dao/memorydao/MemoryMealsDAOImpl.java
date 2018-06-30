package ru.javawebinar.topjava.dao.memorydao;

import ru.javawebinar.topjava.dao.objectsdao.ObjectDAO;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MemoryMealsDAOImpl extends DataSourceInit implements ObjectDAO<Meal> {

    private static volatile MemoryMealsDAOImpl instance;

    private List<Meal> data;
    private MemoryMealsDAOImpl() {
        data = DataSourceInit.getInstance().getMeals();
    }

    public static MemoryMealsDAOImpl getInstance() {
        if (instance == null) {
            synchronized (MemoryMealsDAOImpl.class) {
                if (instance == null) {
                    instance = new MemoryMealsDAOImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Meal> getAll() {
        return data;
    }
}
