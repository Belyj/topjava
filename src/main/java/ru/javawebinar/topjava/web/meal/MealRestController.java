package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.List;

@Controller
public class MealRestController {

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
            return service.get(id);
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

    public List<Meal> getAll() {
        return service.getAll();
    }
}