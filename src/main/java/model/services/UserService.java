package model.services;

import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Dyvak on 23.01.2017.
 */
public interface UserService {

    Optional<User> login(String email, String password) ;

    /**
     * @return list of Users from base.
     */
    List<User> getAll();

    /**
     * @param email email of the User.
     * @return User with specified email
     */
    Optional<User> getByEmail(String email);

    /**
     * @param id id of the User.
     * @return User with specified id
     */
    Optional<User> getById(int id);

    /**
     * Create User.
     */
    void create(User user);

    /**
     * update UserOrder in the base.
     */
    void update(User user);

    /**
     * @param id id of the User.
     * delete User from base.
     */
    void delete(int id);

    /**
     * @return list of Users from base which have Orders.
     */
    List<User> getAllUsersWithOrders();

    /**
     * @param userList is List of Users, this Users will be keys in Map.
     * @return Map where key is Userand value is one more Map with key is Order and value is one more
     * Map with key OrderProduct and value Product (User->Order->OrderProduct->Product)
     */
    Map<User,Map<Order,Map<OrderProduct,Product>>> getUserMap(List<User> userList);
}
