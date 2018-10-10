package model.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by Dyvak on 17.12.2016.
 */
public interface GenericDao<E> {

    /**
     * Inserts object into database
     *
     * @param e instance to insert
     * @return object with updated field Id
     */
    void create(E e);

    /**
     * Updates correspondent to object rows in database
     *
     * @param e instance to update
     */
    void update(E e, int id);

    /**
     * Searches for saved instance by id and removes it from BD
     *
     * @param id instance's field id
     */
    void delete(int id);

    /**
     * @return collection of all instances, saved in db.
     */
    List<E> findAll();

    /**
     * Searches for saved instance by id
     *
     * @param id instance's field id
     * @return Optional of entity with specified Id
     */
    Optional<E> findById(int id);
}
