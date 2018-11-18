package model.dao.daofactory;

import model.dao.*;
import model.dao.connection.DaoConnection;
import model.dao.connection.DaoConnectionFactory;
import model.dao.connection.JdbcDaoConnectionFactory;

import java.sql.Connection;

public class JdbcDaoManager implements DaoManager {

    private JdbcDaoManager() {

    }

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static class Holder {
        static final JdbcDaoManager INSTANCE = new JdbcDaoManager();
    }

    public static JdbcDaoManager getInstance() {
        return JdbcDaoManager.Holder.INSTANCE;
    }

    private DaoConnectionFactory connectionFactory = new JdbcDaoConnectionFactory();

    public ProductDao createProductDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createProductDao(connection);
    }


    public UserDao createUserDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createUserDao(connection);
    }


    public OrderDao createOrderDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createOrderDao(connection);
    }

    public OrderProductDao createOrderProductDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createOrderProductDao(connection);
    }

    public UserOrderDao createUserOrderDao() {
        DaoConnection daoConnection = connectionFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createUserOrderDao(connection);
    }

    public void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
