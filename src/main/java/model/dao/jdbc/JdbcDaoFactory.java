package model.dao.jdbc;

import model.dao.*;
import model.dao.exception.DAOException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class defines DAO Factory
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcDaoFactory extends DaoFactory {

    public static final String JAVA_COMP_ENV_JDBC_MYDB="java:comp/env/jdbc/mydb";

    private Connection connection;
    private static final String DB_URL="url";

    private DataSource dataSource;

    public JdbcDaoFactory() {
    }

    @Override
    public DaoConnection getConnection() {
        try {
            InitialContext ic=new InitialContext();
            dataSource=(DataSource) ic.lookup(JAVA_COMP_ENV_JDBC_MYDB);
            return new JdbcDaoConnection(dataSource.getConnection());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }


    public DaoConnection getConnectionForTesting() {
        try {
            InputStream inputStream=
                    DaoFactory.class.getResourceAsStream(DB_FILE);
            Properties dbProps=new Properties();
            dbProps.load(inputStream);
            String url=dbProps.getProperty(DB_URL);
            connection=DriverManager.getConnection(url, dbProps);
            return new JdbcDaoConnection(connection);
        } catch (IOException | SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public OrderDao createOrderDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection=(JdbcDaoConnection) connection;
        Connection sqlConnection=jdbcConnection.getConnection();
        return new JdbcOrderDao(sqlConnection);
    }

    @Override
    public ProductDao createProductDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection=(JdbcDaoConnection) connection;
        Connection sqlConnection=jdbcConnection.getConnection();
        return new JdbcProductDao(sqlConnection);
    }

    @Override
    public OrderProductDao createOrderProductDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection=(JdbcDaoConnection) connection;
        Connection sqlConnection=jdbcConnection.getConnection();
        return new JdbcOrderProductDao(sqlConnection);
    }

    @Override
    public UserOrderDao createUserOrderDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection=(JdbcDaoConnection) connection;
        Connection sqlConnection=jdbcConnection.getConnection();
        return new JdbcUserOrderDao(sqlConnection);
    }

    @Override
    public UserDao createUserDao(DaoConnection connection) {
        JdbcDaoConnection jdbcConnection=(JdbcDaoConnection) connection;
        Connection sqlConnection=jdbcConnection.getConnection();
        return new JdbcUserDao(sqlConnection);
    }

}
