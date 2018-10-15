package testDao;

import model.dao.jdbc.AbstractDao;
import model.dao.jdbc.JdbcProductDaoImpl;
import model.entities.User;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

public class TestAbstractDao {

    @Mock
    Connection connection;

    @Test
    void testStringConstructor() {

        JdbcProductDaoImpl dao = new JdbcProductDaoImpl(connection);

        Field[] fields = User.class.getDeclaredFields();


        try {
            Method method = AbstractDao.class.getDeclaredMethod("toStringFields", Field[].class);
            method.setAccessible(true);
            String output = (String) method.invoke(dao, fields);
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
