package model.dao.daofactory;

import model.dao.*;
import model.dao.jdbc.*;

import java.sql.Connection;

/**
 * This class defines DAO Factory
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcDaoFactory extends DaoFactory {

    @Override
    public ProductDao createProductDao(Connection connection) {
        return new JdbcProductDaoImpl(connection);
    }

    @Override
    public UserDao createUserDao(Connection connection) {
        return new JdbcUserDaoImpl(connection);
    }

    @Override
    public OrderDao createOrderDao(Connection connection) {
        return new JdbcOrderDaoImpl(connection);
    }

    @Override
    public OrderProductDao createOrderProductDao(Connection connection) {
        return new JdbcOrderProductDaoImpl(connection);
    }

    @Override
    public UserOrderDao createUserOrderDao(Connection connection) {
        return new JdbcUserOrderDaoImpl(connection);
    }
}
