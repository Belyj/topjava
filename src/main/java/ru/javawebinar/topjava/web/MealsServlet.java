package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.controller.Controller;
import ru.javawebinar.topjava.dao.memorydao.DataSourceInit;
import ru.javawebinar.topjava.dao.objectsdao.MealFactoryDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.model.MealWithExceedUI;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    List<Meal> meals;
    private Controller controller;

    @Override
    public void init() {
        log.info("Initialization");
        controller = new Controller();
        meals = controller.getMealFactory().factoryMethod().getAll();
        meals.forEach(x -> System.out.println(x.getCalories()));
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        log.debug("Send meals to jsp");
        ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher;

        List<MealWithExceedUI> mealsWithExceeded = getMealsWithExceededMap(meals);

        req.setAttribute("meals", mealsWithExceeded);

        dispatcher = context.getRequestDispatcher("/meals.jsp");
        dispatcher.include(req, res);
    }

    private static List<MealWithExceed> mapMealsToMealsWithExceed(List<Meal> meals) {
        log.debug("Mapping Meals to Meals with exceed");
        final Integer CALORIES_LIMIT = 2000;
        Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));
        return meals.stream().map(x -> MealsUtil.createWithExceed(x, caloriesSumByDate.get(x.getDate()) > CALORIES_LIMIT)).collect(Collectors.toList());
    }

    private static List<MealWithExceedUI> getMealsWithExceededMap(List<Meal> meals) {
        log.debug("Get meals with exceed for UI");
        List<MealWithExceedUI> ui = new ArrayList<>();
        mapMealsToMealsWithExceed(meals).forEach(meal -> ui.add(new MealWithExceedUI(meal)));
        return ui;
    }
}
