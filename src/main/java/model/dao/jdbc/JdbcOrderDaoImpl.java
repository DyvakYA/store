package model.dao.jdbc;

import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.entities.Order;

import java.sql.Connection;
import java.util.List;

public class JdbcOrderDaoImpl extends AbstractDao<Order> implements GenericDao<Order> {

    private static String TABLE = "orders";

    public JdbcOrderDaoImpl(Connection connection) {
        super(connection, TABLE);
    }

    @Override
    public void create(Order order) {
        super.create(Order.class, order);
    }

    @Override
    public void update(Order order) {
        super.update(Order.class, order, order.getId());
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public Order findOne(long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }
}
