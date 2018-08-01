package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.*;
import ru.javawebinar.topjava.repository.MealRepository;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        return crudRepository.saveWithUserId(meal, userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserId(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getByUserIdAndBetween(startDate, endDate, userId);
    }
}
