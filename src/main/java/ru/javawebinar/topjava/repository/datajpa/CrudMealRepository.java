package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal saveWithUserId(Meal meal, int userId);

    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    boolean deleteByIdAndUserId(int id, int userId);

    Meal getByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserId(int userId);

    List<Meal> getByUserIdAndBetween(LocalDateTime from, LocalDateTime to, int userId);
}
