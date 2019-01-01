package module.testDao;

import model.dao.jdbc.AbstractDao;
import model.dao.jdbc.JdbcProductDaoImpl;
import model.entities.User;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

public class TestAbstractDao {

    private static final Logger log = Logger.getLogger(TestAbstractDao.class);

    @Mock
    Connection connection;

    @Test
    public void testStringConstructor() {

        JdbcProductDaoImpl dao = new JdbcProductDaoImpl(connection);

        Field[] fields = User.class.getDeclaredFields();

        try {
            Method method = AbstractDao.class.getDeclaredMethod("toStringFields", Field[].class);
            method.setAccessible(true);
            String output = (String) method.invoke(dao, fields);
            log.info(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
