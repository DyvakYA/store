package model.dao.daofactory;

import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.entities.Order;
import model.entities.Product;
import model.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Dyvak on 17.12.2016.
 */
public abstract class DaoFactory {

    public abstract DaoConnection getDaoConnection();

    public abstract GenericDao<Product> createProductDao(Connection connection);

    public abstract GenericDao<User> createUserDao(Connection connection);

    public abstract GenericDao<Order> createOrderDao(Connection connection);

    public static final String DB_FILE = "/db.properties";
    private static final String DB_FACTORY_CLASS = "factory.class";
    private static DaoFactory instance;

    public static DaoFactory getInstance() {
        if (instance == null) {
            try {
                InputStream inputStream =
                        DaoFactory.class.getResourceAsStream(DB_FILE);
                Properties dbProps = new Properties();
                dbProps.load(inputStream);
                String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
                instance = (DaoFactory) Class.forName(factoryClass).getDeclaredConstructor().newInstance();
            } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException |
                    InstantiationException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}