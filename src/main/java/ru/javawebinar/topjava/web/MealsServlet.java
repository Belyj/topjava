package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
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

    @Override
    public void init() {
        log.info("Инициализация");
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
                             );
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        log.debug("Send meas to jsp");
        ServletContext context = this.getServletContext();
        RequestDispatcher dispatcher;

        Map<String, MealWithExceed> mealsWithExceeded = getMealsWithExceededMap(meals);

        req.setAttribute("meals", mealsWithExceeded);
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");

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

    private static Map<String, MealWithExceed> getMealsWithExceededMap(List<Meal> meals) {
        log.debug("Get map of meals with exceed");
        return mapMealsToMealsWithExceed(meals).stream().collect(Collectors.toMap(x -> x.getDateTime().toString().replace("T", " "), x -> x));
    }
}
