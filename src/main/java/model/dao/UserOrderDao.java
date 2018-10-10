package model.dao;

import model.entities.Order;
import model.entities.UserOrder;

import java.util.List;

public interface UserOrderDao extends GenericDao<UserOrder> {

    List<Order> findAllOrdersForUser(int user);
}

