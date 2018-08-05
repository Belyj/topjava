package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.*;
import java.util.List;

@Controller
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String meals(Model model, Integer userId) {
        model.addAttribute("meals", mealService.getAll(userId));
        return "meals";
    }

    @GetMapping("/meal")
    public String meal(Model model, Integer mealId, Integer userId) {
        model.addAttribute("meal", mealService.get(mealId, userId));
        return "meal";
    }

    public void delete(Integer mealId, Integer userId) {
        mealService.delete(mealId, userId);
    }

    public void update(Meal meal, Integer userId) {
        mealService.update(meal, userId);
    }

    @GetMapping("/meals")
    public String getBetweenDates(Model model, LocalDate startDate, LocalDate endDate, int userId) {
        model.addAttribute("meals", mealService.getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId));
        return "meals";
    }

    @GetMapping("/meals")
    public String getBetweenDateTimes(Model model, LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        model.addAttribute("meals", mealService.getBetweenDateTimes(startDateTime, endDateTime, userId));
        return "meals";
    }

    @PostMapping("/meal")
    public String create(Model model, Meal meal, int userId) {
        model.addAttribute("meal", mealService.create(meal, userId));
        return "meal";
    }

    @GetMapping("/meal")
    public String getWithUser(Model model, Integer mealId, int userId) {
        model.addAttribute("meal", mealService.getWithUser(mealId, userId));
        return "meal";
    }
}
