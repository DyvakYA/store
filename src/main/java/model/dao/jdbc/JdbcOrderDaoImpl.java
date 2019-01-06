package model.dao.jdbc;

import model.dao.GenericDao;
import model.dao.OrderDao;
import model.dao.connection.DaoConnection;
import model.entities.Order;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcOrderDaoImpl extends AbstractDao<Order> implements OrderDao {

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
    public Optional<Order> findOne(long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void updateOrderStatus(Order order, int id) {

    }
}
