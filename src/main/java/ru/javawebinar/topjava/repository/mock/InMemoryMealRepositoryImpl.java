package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

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
    public void delete(int id, User user) {
        if (repository.get(id) != null && repository.get(id).getUserId().equals(user.getId())) {
            repository.remove(id);
        } else throw new NotFoundException(String.format("Meal with id = %s does not exist", id));
    }

    @Override
    public Meal get(int id, User user) {
        if ( repository.get(id) != null && repository.get(id).getUserId().equals(user.getId())) {
            return repository.get(id);
        }
        return null;
//        throw new NotFoundException("Meal with this ID does not exist");
    }

    @Override
    public Collection<Meal> getAll(User user) {
        return repository.values().stream().filter(x -> x.getUserId().equals(user.getId())).sorted((m, m2) -> m2.getDate().compareTo(m.getDate())).collect(Collectors.toList());
    }
}

