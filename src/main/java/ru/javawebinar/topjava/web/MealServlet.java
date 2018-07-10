package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private User user;
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            controller = appCtx.getBean(MealRestController.class);
            user = appCtx.getBean(ProfileRestController.class).get(SecurityUtil.authUserId());
            controller.getAll(user.getId());
        }
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String button = request.getParameter("button");

        if ("button".equals(button)) {
            log.info("buttonFilter");
            request.setAttribute("meals", buttonFilter(request.getParameter("fromDate"),
                                                       request.getParameter("toDate"),
                                                       request.getParameter("fromTime"),
                                                       request.getParameter("toTime")));


            request.getAttribute("meals");
        }
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        response.sendRedirect("meals");
    }

    private List<MealWithExceed> buttonFilter(String fromDate, String toDate, String fromTime, String toTime) {
        return DateTimeUtil.isBetween(MealsUtil.getWithExceeded(controller.getAll(user.getId()), MealsUtil.DEFAULT_CALORIES_PER_DAY),
                               fromDate,
                               toDate,
                               fromTime,
                               toTime);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
                controller.create(new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, user.getId()));
                break;
            case "update":
                Meal meal = controller.get(getId(request), user.getId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
                log.info("getAll");
                request.setAttribute("meals",
                                     MealsUtil.getWithExceeded(controller.getAll(user.getId()),
                                                               MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
