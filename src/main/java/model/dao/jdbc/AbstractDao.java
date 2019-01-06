package model.dao.jdbc;

import model.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 5/27/2018.
 */
public abstract class AbstractDao<T> {

    private static final Logger log = Logger.getLogger(AbstractDao.class);

    private final String ID = "id=?";
    private final String WHERE = " WHERE ";
    private final String FROM = " FROM ";
    private final String SELECT = "SELECT *";
    private final String DELETE = "DELETE";
    private final String UPDATE = "UPDATE ";
    private final String INSERT_INTO = "INSERT INTO ";
    private final String SET = " SET ";
    private final String VALUES = "VALUES";

    protected final Connection connection;
    private final String table;

    public AbstractDao(Connection connection, String table) {
        log.info("Connection = " + connection + " Table = " + table);
        this.connection = connection;
        this.table = table;
    }

    public List<T> findAll(Class<T> cls) {
        List<T> result = new ArrayList<>();
        try {
            try (Statement st = connection.createStatement()) {
                try (ResultSet query = st.executeQuery(SELECT + FROM + table)) {
                    log.info(query);
                    ResultSetMetaData md = query.getMetaData();
                    while (query.next()) {
                        T client = (T) cls.getDeclaredConstructor().newInstance();
                        objectConstructor(cls, query, md, client);
                        result.add(client);
                    }
                }
            }
            return result;
        } catch (InstantiationException | IllegalAccessException |
                NoSuchFieldException | SQLException |
                NoSuchMethodException | InvocationTargetException e) {
            throw new DAOException(e);
        }
    }

    public T findOne(Class<T> cls, long id) {
        try {
            T client = (T) cls.newInstance();
            try (PreparedStatement query = connection.prepareStatement(SELECT + FROM + table + WHERE + ID)) {
                query.setLong(1, id);
                log.info(query);
                ResultSet resultSet = query.executeQuery();
                ResultSetMetaData md = resultSet.getMetaData();
                if (resultSet.next()) {
                    objectConstructor(cls, resultSet, md, client);
                }
            }
            return client;
        } catch (InstantiationException | SQLException | IllegalAccessException | NoSuchFieldException e) {
            throw new DAOException(e);
        }

    }

    public void create(Class<T> cls, T t) {
        try {
            Field[] fields = cls.getDeclaredFields();

            //List<Field> list = Arrays.asList(fields);
            //Field id = list.get(0);
            //list.remove(id);
            // list.remove(0);

            LinkedList<Field> list = new LinkedList<>(Arrays.asList(fields));
            list.removeFirst();

            log.info(list);

            String stringFields = toStringFields(list);
            String stringParameters = toStringCountParameters(list);
            try (PreparedStatement query = connection.prepareStatement(INSERT_INTO + table + stringFields + VALUES + stringParameters)) {
                log.info(query);
                setColumnsFromObject(t, query, list);
                log.info(query);
                query.executeUpdate();
            }
        } catch (IllegalAccessException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void update(Class<T> cls, T t, long id) {
        Field[] fields = cls.getDeclaredFields();
        LinkedList<Field> list = new LinkedList<>(Arrays.asList(fields));
        String stringFields = toStringFields(list);
        try (PreparedStatement query = connection.prepareStatement(UPDATE + table + SET + stringFields + WHERE + ID)) {
            query.setLong(1, id);
            setColumnsFromObject(t, query, list);
            log.info(query);
            query.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new DAOException(e);
        }
    }

    public void delete(long id) {
        try (PreparedStatement query = connection.prepareStatement(DELETE + FROM + table + WHERE + ID)) {
            query.setLong(1, id);
            log.info(query);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void objectConstructor(Class<T> cls, ResultSet resultSet, ResultSetMetaData md, T object)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        for (int i = 1; i <= md.getColumnCount(); i++) {
            String columnName = md.getColumnName(i);
            Field field = cls.getDeclaredField(columnName);
            field.setAccessible(true);
            getColumn(resultSet, object, columnName, field);
        }
    }

    private void getColumn(ResultSet resultSet, T client, String columnName, Field field) throws IllegalAccessException, SQLException {
        if (field.getType().equals(int.class)) {
            field.set(client, Integer.valueOf(resultSet.getString(columnName)));
        }
        if (field.getType().equals(long.class)) {
            field.set(client, Long.valueOf(resultSet.getString(columnName)));
        }
        if (field.getType().equals(String.class)) {
            field.set(client, resultSet.getString(columnName));
        }
    }

    private PreparedStatement setColumnsFromObject(T t, PreparedStatement query, List<Field> fields) throws SQLException, IllegalAccessException {
        for (int i = 0; i < fields.size(); i++) {
            fields.get(i).setAccessible(true);
            if (fields.get(i).getType().equals(long.class)) {
                query.setLong(i + 1, Long.valueOf(String.valueOf(fields.get(i).get(t))));
            } else if (fields.get(i).getType().equals(int.class)) {
                query.setInt(i + 1, Integer.valueOf(String.valueOf(fields.get(i).get(t))));
            } else if (fields.get(i).getType().equals(boolean.class)) {
                query.setBoolean(i + 1, Boolean.valueOf(String.valueOf(fields.get(i).get(t))));
            } else {
                query.setString(i + 1, String.valueOf(fields.get(i).get(t)));
            }
        }
        return query;
    }

    private String toStringFields(List<Field> fields) {
        return new StringBuilder("(").append(fields.stream()
                .map(s -> s.getName())
                .collect(Collectors.joining(","))).append(")").toString();
    }


    private String toStringCountParameters(List<Field> fields) {
        return new StringBuilder("(").append(fields.stream()
                .map(s -> new String("?"))
                .collect(Collectors.joining(","))).append(")").toString();
    }
}
