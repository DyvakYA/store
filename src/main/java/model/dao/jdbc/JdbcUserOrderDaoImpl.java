package model.dao.jdbc;

import model.dao.UserOrderDao;
import model.entities.Order;
import model.entities.UserOrder;

import java.sql.Connection;
import java.util.List;

public class JdbcUserOrderDaoImpl extends AbstractDao<UserOrder> implements UserOrderDao {

    public JdbcUserOrderDaoImpl(Connection connection) {
        super(connection, "user_order");
    }

    @Override
    public void create(UserOrder userOrder) {

    }

    @Override
    public void update(UserOrder userOrder) {

    }

    @Override
    public UserOrder findOne(long id) {
        return null;
    }

    @Override
    public List<UserOrder> findAll() {
        return null;
    }

    @Override
    public List<Order> findAllOrdersForUser(int user) {
        return null;
    }
}
