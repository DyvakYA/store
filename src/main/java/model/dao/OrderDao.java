package model.dao;

import model.entities.Order;

/**
 * Created by Dyvak on 24.12.2016.
 */
public interface OrderDao extends GenericDao<Order> {

    /**
     * Updates correspondent to Order row Status in database
     *
     * @param id
     *            instance to update
     */
    void updateOrderStatus(Order order, int id);
}