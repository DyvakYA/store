package model.dao.daofactory;

import model.dao.*;
import model.dao.connection.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by Dyvak on 17.12.2016.
 */
public abstract class DaoFactory {

    public abstract ProductDao createProductDao();

    public abstract UserDao createUserDao();

    public abstract OrderDao createOrderDao();

    public abstract OrderProductDao createOrderProductDao();

    public abstract UserOrderDao createUserOrderDao();

    public abstract void setConnection(Connection connection);
}