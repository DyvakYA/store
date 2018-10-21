package model.dao.daofactory;

import model.dao.daofactory.DaoFactory;
import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.dao.connection.JdbcDaoConnection;
import model.dao.jdbc.JdbcOrderDaoImpl;
import model.dao.jdbc.JdbcProductDaoImpl;
import model.dao.jdbc.JdbcUserDaoImpl;
import model.entities.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * This class defines DAO Factory
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcDaoFactory extends DaoFactory {

    @Override
    public GenericDao<Product> createProductDao(Connection connection) {
        return new JdbcProductDaoImpl(connection);
    }

    @Override
    public GenericDao<User> createUserDao(Connection connection) {
        return new JdbcUserDaoImpl(connection);
    }

    @Override
    public GenericDao<Order> createOrderDao(Connection connection) {
        return new JdbcOrderDaoImpl(connection);
    }

    @Override
    public GenericDao<OrderProduct> createOrderProductDao(Connection connection) {
        return new JdbcOrderProductDaoImpl(connection);
    }

    @Override
    public GenericDao<UserOrder> createUserOrderDao(Connection connection) {
        return new JdbcUserOrderDaoImpl(connection);
    }
}
