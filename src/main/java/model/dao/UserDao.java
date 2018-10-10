package model.dao;

import model.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by Dyvak on 24.12.2016.
 */
public interface UserDao extends GenericDao<User> {

    /**
     * Searches for user with specified email.
     *
     * @param email instance's field email
     * @return Optional of entity with specified email
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Searches all users in base.
     *
     * @return list of User.
     */
    List<User> findAllUsersWithOrders();

    /**
     * Get password from base for concrete user.
     *
     * @return String password of concrete user.
     */
    String getPasswordForUser(User user);
}