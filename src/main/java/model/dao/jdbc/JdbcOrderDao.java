package model.dao.jdbc;

import model.dao.OrderDao;
import model.dao.exception.DAOException;
import model.entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.constants.ErrorMsgHolder.SQL_EXCEPTION;

/**
 * This class is the implementation of Order entity DAO
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcOrderDao extends AbstractDao<Order> implements OrderDao {

    private static final String SELECT_FROM_ORDERS_WHERE_ORDER_ID="SELECT * FROM orders WHERE order_id=?";
    private static final String SELECT_FROM_ORDERS="SELECT * FROM orders";
    private static final String CREATE_ORDER_QUERY="INSERT INTO orders (order_status)  VALUES (?)";
    private static final String UPDATE_ORDER_QUERY="UPDATE orders SET order_status=?, order_sum=? WHERE order_id=?";
    private static final String DELETE_ORDER_QUERY="DELETE FROM orders WHERE order_id=?";

    private static final String UPDATE_ORDER_STATUS_QUERY="UPDATE orders SET order_status=? WHERE order_id=?";

    JdbcOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<Order> findById(int id) {
        return getOrder(id, SELECT_FROM_ORDERS_WHERE_ORDER_ID);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders=new ArrayList<>();
        try (PreparedStatement query=connection.prepareStatement(SELECT_FROM_ORDERS)) {
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                orders.add(resultSetExtractor.getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(Order order, int id) {
        checkForNull(order);
        checkIsSaved(order);
        try (PreparedStatement query=connection.prepareStatement(UPDATE_ORDER_STATUS_QUERY)) {
            query.setString(1, order.getOrderStatus());
            query.setInt(2, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + order.toString(), e);
        }
    }

    @Override
    public void create(Order order) {
        checkForNull(order);
        checkIsUnsaved(order);
        try (PreparedStatement query=connection
                .prepareStatement(CREATE_ORDER_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            query.setString(1, order.getOrderStatus());
            query.executeUpdate();
            ResultSet resultSet=query.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + order.toString(), e);
        }
    }

    @Override
    public void update(Order order, int id) {
        checkForNull(order);
        checkIsSaved(order);
        try (PreparedStatement query=connection.prepareStatement(UPDATE_ORDER_QUERY)) {
            query.setString(1, order.getOrderStatus());
            query.setLong(2, order.getTotalPrice());
            query.setInt(3, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + order.toString(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement query=connection.prepareStatement(DELETE_ORDER_QUERY)) {
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }
}
