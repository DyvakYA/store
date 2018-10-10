package model.dao.jdbc;

import model.dao.UserOrderDao;
import model.dao.exception.DAOException;
import model.entities.Order;
import model.entities.UserOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.constants.ErrorMsgHolder.SQL_EXCEPTION;

/**
 * This class is the implementation of UserOrder entity DAO
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcUserOrderDao extends AbstractDao<UserOrder> implements UserOrderDao {

    private static final String SELECT_FROM_USER_ORDER_WHERE_USER_ID="SELECT * FROM user_orders WHERE user_id=?";
    private static final String SELECT_FROM_USER_ORDERS="SELECT * FROM user_orders";
    private static final String SELECT_ORDERS_FOR_USER_BY_ID="SELECT " +
            "Orders.order_id, Orders.order_status, Orders.order_date, Orders.order_sum\n" +
            "FROM Orders\n" +
            "INNER JOIN user_orders ON orders.order_id=user_orders.order_id\n" +
            "WHERE user_id=?;";
    private static final String CREATE_USER_ORDER_QUERY="INSERT INTO user_orders (user_id , order_id)  " +
            "VALUES (?, ?)";
    private static final String UPDATE_USER_ORDERS_QUERY="UPDATE user_orders SET order_id=?" +
            "WHERE user_id=?";
    private static final String DELETE_USER_ORDERS_QUERY="DELETE FROM user_orders WHERE user_id=?";

    JdbcUserOrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<UserOrder> findById(int id) {
        Optional<UserOrder> routeStops=Optional.empty();
        try (PreparedStatement query=connection.prepareStatement(SELECT_FROM_USER_ORDER_WHERE_USER_ID)) {
            query.setInt(1, id);
            ResultSet resultSet=query.executeQuery();
            if (resultSet.next()) {
                routeStops=Optional.of(resultSetExtractor.getUserOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return routeStops;
    }

    @Override
    public List<UserOrder> findAll() {
        List<UserOrder> userOrders=new ArrayList<>();
        try (PreparedStatement query=connection.prepareStatement(SELECT_FROM_USER_ORDERS)) {
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                userOrders.add(resultSetExtractor.getUserOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return userOrders;
    }

    @Override
    public List<Order> findAllOrdersForUser(int id) {
        List<Order> orders=new ArrayList<>();
        try (PreparedStatement query=connection.prepareStatement(SELECT_ORDERS_FOR_USER_BY_ID)) {
            query.setInt(1, id);
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
    public void create(UserOrder userOrder) {
        checkForNull(userOrder);
        checkIsUnsaved(userOrder);
        try (PreparedStatement query=connection.prepareStatement(CREATE_USER_ORDER_QUERY)) {
            query.setInt(1, userOrder.getUserId());
            query.setInt(2, userOrder.getOrderId());
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public void update(UserOrder userOrder, int id) {
        checkForNull(userOrder);
        checkIsSaved(userOrder);
        try (PreparedStatement query=connection.prepareStatement(UPDATE_USER_ORDERS_QUERY)) {
            query.setInt(1, userOrder.getOrderId());
            query.setInt(2, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement query=connection.prepareStatement(DELETE_USER_ORDERS_QUERY)) {
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }
}
