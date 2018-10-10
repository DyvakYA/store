package model.services;

import model.entities.Order;
import model.entities.UserOrder;

import java.util.List;

/**
 * This class represents UserOrder service
 *
 * @author dyvakyurii@gmail.com
 */
public interface UserOrderService {

    /**
     * @return list of UserOrders from base.
     */
    List<UserOrder> getAll();

    /**
     * Create UserOrder.
     */
    void create(UserOrder userOrder);

    /**
     * update UserOrder in the base.
     */
    void update(UserOrder userOrder);

    /**
     * @param id id of the UserOrder.
     * delete UserOrder from base.
     */
    void delete(int id);

    /**
     * @param userId id of the User.
     * @return list of Orders from base where specified userId.
     */
    List<Order> getOrdersForUser(int userId);
}
