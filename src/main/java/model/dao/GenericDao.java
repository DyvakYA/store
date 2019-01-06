package model.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by User on 5/27/2018.
 */
public interface GenericDao<T> {

    void create(T t);

    void update(T t);

    void delete(long id);

    Optional<T> findOne(long id);

    List<T> findAll();
}
