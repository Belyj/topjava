package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = NotFoundException.class)
    public void get() {
        service.get(MealTestData.MEAL_1.getId(), UserTestData.ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(MealTestData.MEAL_1.getId(), UserTestData.ADMIN_ID);
    }

    @Test
    public void getAll() {
        assertTrue(MealTestData.containsAll(service.getAll(SecurityUtil.authUserId())));
    }

    @Test(expected = NotFoundException.class)
    public void update() {
        service.update(new Meal(MealTestData.MEAL_1.getId(), MealTestData.MEAL_1.getDateTime(), MealTestData.MEAL_1.getDescription(), MealTestData.MEAL_1.getCalories(), UserTestData.ADMIN_ID), UserTestData.USER_ID);
    }
}