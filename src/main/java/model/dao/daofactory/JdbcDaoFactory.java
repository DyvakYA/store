package model.dao.daofactory;

import model.dao.*;
import model.dao.jdbc.*;
import org.apache.log4j.Logger;

import java.sql.Connection;

/**
 * This class defines DAO Factory
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcDaoFactory extends DaoFactory {

    private static final Logger log = Logger.getLogger(JdbcDaoFactory.class);

    private Connection connection;

    private JdbcDaoFactory() {
    }

    private static class Holder {
        static final JdbcDaoFactory INSTANCE = new JdbcDaoFactory();
    }

    public static JdbcDaoFactory getInstance() {
        return JdbcDaoFactory.Holder.INSTANCE;
    }

    @Override
    public ProductDao createProductDao() {
        log.info("Connection " + connection);
        return new JdbcProductDaoImpl(connection);
    }

    @Override
    public UserDao createUserDao() {
        return new JdbcUserDaoImpl(connection);
    }

    @Override
    public OrderDao createOrderDao() {
        return new JdbcOrderDaoImpl(connection);
    }

    @Override
    public OrderProductDao createOrderProductDao() {
        return new JdbcOrderProductDaoImpl(connection);
    }

    @Override
    public UserOrderDao createUserOrderDao() {
        return new JdbcUserOrderDaoImpl(connection);
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
