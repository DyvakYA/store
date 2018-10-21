package model.dao.jdbc;

import model.dao.exception.DAOException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 5/27/2018.
 */
public abstract class AbstractDao<T> {

    private final String ID = "id=?";
    private final String WHERE = " WHERE ";
    private final String FROM = " FROM ";
    private final String SELECT = "SELECT *";
    private final String DELETE = "DELETE";
    private final String UPDATE = "UPDATE ";
    private final String INSERT_INTO = "INSERT INTO ";
    private final String SET = " SET ";
    private final String VALUES = "VALUES";

    private final Connection connection;
    private final String table;

    public AbstractDao(Connection connection, String table) {
        this.connection = connection;
        this.table = table;
    }

    public List<T> findAll(Class<T> cls) {
        List<T> result = new ArrayList<>();
        try {
            try (Statement st = connection.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(SELECT + FROM + table)) {
                    ResultSetMetaData md = resultSet.getMetaData();
                    while (resultSet.next()) {
                        T client = (T) cls.newInstance();
                        objectConstructor(cls, resultSet, md, client);
                        result.add(client);
                    }
                }
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public T findOne(Class<T> cls, long id) {
        try {
            T client = (T) cls.newInstance();
            try (PreparedStatement query = connection.prepareStatement(SELECT + FROM + table + WHERE + ID)) {
                query.setLong(1, id);
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
            String stringFields = toStringFields(fields);
            String stringParameters = toStringCountParameters(fields);
            try (PreparedStatement query = connection.prepareStatement(INSERT_INTO + table + stringFields + VALUES + stringParameters)) {
                setColumnsFromObject(t, query, fields);
                query.executeUpdate();
            }
        } catch (IllegalAccessException | SQLException e) {
            throw new DAOException(e);
        }
    }

    public void update(Class<T> cls, T t, long id) {
        Field[] fields = cls.getDeclaredFields();
        String s = toStringFields(fields);
        try (PreparedStatement query = connection.prepareStatement(UPDATE + table + SET + s + WHERE + ID)) {
            query.setLong(1, id);
            setColumnsFromObject(t, query, fields);
            query.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new DAOException(e);
        }
    }

    public void delete(long id) {
        try (PreparedStatement query = connection.prepareStatement(DELETE + FROM + table + WHERE + ID)) {
            query.setLong(1, id);
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

    private void setColumnsFromObject(T t, PreparedStatement query, Field[] fields) throws SQLException, IllegalAccessException {
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            if (fields[i].getType().equals(long.class)) {
                query.setLong(i, Long.valueOf(String.valueOf(fields[i].get(t))));
            } else if (fields[i].getType().equals(int.class)) {
                query.setInt(i, Integer.valueOf(String.valueOf(fields[i].get(t))));
            } else {
                query.setString(i, String.valueOf(fields[i].get(t)));
            }
        }
    }

    private String toStringFields(Field[] fields) {
        return new StringBuilder("(").append(Arrays.stream(fields)
                .map(s -> s.getName())
                .collect(Collectors.joining(","))).append(")").toString();
    }


    private String toStringCountParameters(Field[] fields) {
        return new StringBuilder("(").append(Arrays.stream(fields)
                .map(s -> new String("?"))
                .collect(Collectors.joining(","))).append(")").toString();
    }
}
