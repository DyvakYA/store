package model.dao.daofactory;

import model.dao.daofactory.DaoFactory;
import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.dao.connection.JdbcDaoConnection;
import model.dao.jdbc.JdbcOrderDaoImpl;
import model.dao.jdbc.JdbcProductDaoImpl;
import model.dao.jdbc.JdbcUserDaoImpl;
import model.entities.Order;
import model.entities.Product;
import model.entities.User;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * This class defines DAO Factory
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcDaoFactory extends DaoFactory {

    public static final String JAVA_COMP_ENV_JDBC_MYDB = "java:comp/env/jdbc/mydb";

    private DataSource dataSource;

    @Override
    public DaoConnection getDaoConnection() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(JAVA_COMP_ENV_JDBC_MYDB);
            return new JdbcDaoConnection(dataSource.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
}
