package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MealRestController {

    private MealService service;

    public MealRestController() {
        service = new MealServiceImpl(new InMemoryMealRepositoryImpl());
    }

    public Meal get(int id, int userId) {
        if (service.get(id) != null && service.get(id).getUserId() == userId) {
            return service.get(id);
        }
        throw new NotFoundException(String.format("Meal with id = %s does not exist", id));
    }

    public void save(Meal meal) {
        service.save(meal);
    }

    public Meal create(Meal meal) {
        return service.create(meal);
    }

    public void update(Meal meal) {
        service.update(meal);
    }

    public void delete(int mealId) {
        if (service.get(mealId) != null) {
            service.delete(mealId);
            return;
        }
        throw new NotFoundException(String.format("Meal with id = %s does not exist", mealId));
    }

    public List<Meal> getAll(int userId) {
        return service.getAll().stream().filter(x -> x.getUserId() == userId).collect(Collectors.toList());
    }
}