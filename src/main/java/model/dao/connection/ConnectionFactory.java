package model.dao.connection;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public abstract class ConnectionFactory {

    private static final Logger log = Logger.getLogger(JdbcConnectionFactory.class);

    public abstract DaoConnection getConnection();

    public static final String DB_FILE = "/db.properties";
    private static final String DB_FACTORY_CLASS = "factory.class";
    private static ConnectionFactory instance;

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            try {
                InputStream inputStream =
                        ConnectionFactory.class.getResourceAsStream(DB_FILE);
                Properties dbProps = new Properties();
                dbProps.load(inputStream);
                log.info(dbProps.toString());
                String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
                instance = (ConnectionFactory) Class.forName(factoryClass).getDeclaredConstructor().newInstance();
            } catch (IOException | IllegalAccessException |
                    InstantiationException | ClassNotFoundException |
                    NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
