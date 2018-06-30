package ru.javawebinar.topjava.dao.objectsdao;

import java.util.List;

public interface ObjectDAO<T> {

    /**
     * getting all objects of T type
     * @return List<T>
     */
    List<T> getAll();
}
