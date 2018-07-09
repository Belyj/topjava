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

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private User user = new ProfileRestController().get(SecurityUtil.authUserId());
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            controller = appCtx.getBean(MealRestController.class);
            controller.getAll(user.getId());
        }
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String button = request.getParameter("button");

        if ("button".equals(button)) {
            String fromDate = request.getParameter("toDate");
            String toDate = request.getParameter("fromDate");
            String fromTime = request.getParameter("fromTime");
            String toTime = request.getParameter("toTime");

            buttonFilter(fromDate, toDate, fromTime, toTime);

            log.info("getAll");
            request.setAttribute("meals", buttonFilter(fromDate, toDate, fromTime, toTime));

            request.getAttribute("meals");
        } else {
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                                 LocalDateTime.parse(request.getParameter("dateTime")),
                                 request.getParameter("description"),
                                 Integer.parseInt(request.getParameter("calories")), user.getId());

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            controller.save(meal);
        }

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        response.sendRedirect("meals");
    }

    private List<MealWithExceed> buttonFilter(String fromDate, String toDate, String fromTime, String toTime) {

        List<MealWithExceed> mealWithExceeds = MealsUtil.getWithExceeded(controller.getAll(user.getId()),
                                                         MealsUtil.DEFAULT_CALORIES_PER_DAY);
        if (fromDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
            mealWithExceeds.stream().filter(x -> DateTimeUtil.isFromDate(x.getDateTime().toLocalDate(), LocalDate.parse(fromDate, formatter)));
        }

        if (toDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
            mealWithExceeds.stream().filter(x -> DateTimeUtil.isToDate(x.getDateTime().toLocalDate(), LocalDate.parse(toDate, formatter)));
        }

        if (fromTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            mealWithExceeds.stream().filter(x -> DateTimeUtil.isFromTime(x.getDateTime().toLocalTime(), LocalTime.parse(fromTime, formatter)));
        }

        if (fromTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            mealWithExceeds.stream().filter(x -> DateTimeUtil.isToTime(x.getDateTime().toLocalTime(), LocalTime.parse(fromTime, formatter)));
        }

        return mealWithExceeds;
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
            default:
                log.info("getAll");
                request.setAttribute("meals",
                                     MealsUtil.getWithExceeded(controller.getAll(user.getId()),
                                                               MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
