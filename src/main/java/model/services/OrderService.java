package model.services;

import model.entities.Order;

import java.util.List;

/**
 * This class represents Order service
 *
 * @author dyvakyurii@gmail.com
 */
public interface OrderService {

    /**
     * @return list of Orders from base.
     */
    List<Order> getAll();

    /**
     * Create Order.
     */
    void create(Order order);

    /**
     * update Order in the base.
     */
    void update(Order order);

    /**
     * @param id id of the Order.
     * delete Order from base.
     */
    void delete(int id);

    /**
     * Create default Order with default values.
     */
    Order createDefaultOrder();

    /**
     * update Order status in the base.
     */
    void updateOrderStatus(Order order);
}

