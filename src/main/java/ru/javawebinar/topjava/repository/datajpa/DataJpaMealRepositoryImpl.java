package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        crudRepository.deleteById(id);
        return !crudRepository.findById(id).isPresent();
    }

    @Override
    public Meal get(int id, int userId) {
        Optional<Meal> meal = crudRepository.findById(id);
        return meal.isPresent() && meal.get().getUser().getId().equals(userId) ? meal.get() : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll().stream().filter(x -> x.getUser().getId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getAll(userId).stream().filter(x -> x.getDateTime().isAfter(startDate) && x.getDateTime().isBefore(endDate)).collect(Collectors.toList());
    }
}
