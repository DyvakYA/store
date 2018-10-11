package model.dao;

import java.util.List;

/**
 * Created by User on 5/27/2018.
 */
public interface GenericDao<T> {

    void create(T t);

    void update(T t);

    void delete(long id);

    T findOne(long id);

    List<T> findAll();
}
