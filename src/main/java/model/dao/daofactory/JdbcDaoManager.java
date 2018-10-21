package model.dao.daofactory;

import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.dao.connection.DaoConnectionFactory;
import model.dao.connection.JdbcDaoConnectionFactory;
import model.entities.*;

import java.sql.Connection;

public class JdbcDaoManager implements DaoManager{

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private DaoConnectionFactory connectionFactory = new JdbcDaoConnectionFactory();

    public GenericDao<Product> createProductDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createProductDao(connection);
    }


    public GenericDao<User> createUserDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createUserDao(connection);
    }


    public GenericDao<Order> createOrderDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createOrderDao(connection);
    }

    public GenericDao<OrderProduct> createOrderProductDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createOrderProductDao(connection);
    }

    public GenericDao<UserOrder> createUserOrderDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createUserOrderDao(connection);
    }

    public void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
