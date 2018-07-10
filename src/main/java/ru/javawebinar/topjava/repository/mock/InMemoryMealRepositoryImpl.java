package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.UserServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private int userId = SecurityUtil.authUserId();

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public void delete(int id) {
        if (repository.get(id).getUserId().equals(userId)) {
            repository.remove(id);
        }
        throw new NotFoundException("Meal with this ID does not exist");
    }

    @Override
    public Meal get(int id) {
        if (repository.get(id).getUserId().equals(userId)) {
            return repository.get(id);
        }
        throw new NotFoundException("Meal with this ID does not exist");
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream().filter(x -> x.getUserId().equals(userId)).collect(Collectors.toList());
    }
}

