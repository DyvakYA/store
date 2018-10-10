package model.dao.jdbc;

import model.dao.GenericDao;
import model.dao.exception.DAOException;
import model.entities.Identified;
import model.entities.Order;
import model.entities.Product;
import model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.constants.ErrorMsgHolder.SQL_EXCEPTION;

/**
 * Created by User on 5/11/2017.
 */
abstract class AbstractDao<E> implements GenericDao<E> {

//    private final String ID = "id=?";
//    private final String WHERE = " WHERE ";
//    private final String FROM = " FROM ";
//    private final String SELECT = "SELECT *";
//    private final String DELETE = "DELETE";
//    private final String UPDATE = "UPDATE ";
//    private final String INSERT_INTO = "INSERT INTO ";
//    private final String SET = " SET ";
//    private final String VALUES = "VALUES";
//
//    private final Connection connection;
//    private final String table;
//
//    public AbstractDao(Connection connection, String table) {
//        this.connection = connection;
//        this.table = table;
//    }
//
//    public List<T> findAll(Class<T> cls) {
//        List<T> result = new ArrayList<>();
//        try {
//            try (Statement st = connection.createStatement()) {
//                try (ResultSet resultSet = st.executeQuery(SELECT + FROM + table)) {
//                    ResultSetMetaData md = resultSet.getMetaData();
//                    while (resultSet.next()) {
//                        T client = (T) cls.newInstance();
//                        objectConstructor(cls, resultSet, md, client);
//                        result.add(client);
//                    }
//                }
//            }
//            return result;
//        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SQLException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    public T findOne(Class<T> cls, long id) {
//        try {
//            T client = (T) cls.newInstance();
//            try (PreparedStatement query = connection.prepareStatement(SELECT + FROM + table + WHERE + ID)) {
//                query.setLong(1, id);
//                ResultSet resultSet = query.executeQuery();
//                ResultSetMetaData md = resultSet.getMetaData();
//                if (resultSet.next()) {
//                    objectConstructor(cls, resultSet, md, client);
//                }
//            }
//            return client;
//        } catch (InstantiationException | SQLException | IllegalAccessException | NoSuchFieldException e) {
//            throw new DaoException(e);
//        }
//
//    }
//
//    public void create(Class<T> cls, T t) {
//        try {
//            Field[] fields = cls.getDeclaredFields();
//            String stringFields = toStringFields(fields);
//            String stringParameters = toStringCountParameters(fields);
//            try (PreparedStatement query = connection.prepareStatement(INSERT_INTO + table + stringFields + VALUES + stringParameters)) {
//                setColumnsFromObject(t, query, fields);
//                query.executeUpdate();
//            }
//        } catch (IllegalAccessException | SQLException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    public void update(Class<T> cls, T t, long id) {
//        Field[] fields = cls.getDeclaredFields();
//        String s = toStringFields(fields);
//        try (PreparedStatement query = connection.prepareStatement(UPDATE + table + SET + s + WHERE + ID)) {
//            query.setLong(1, id);
//            setColumnsFromObject(t, query, fields);
//            query.executeUpdate();
//        } catch (SQLException | IllegalAccessException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    public void delete(long id) {
//        try (PreparedStatement query = connection.prepareStatement(DELETE + FROM + table + WHERE + ID)) {
//            query.setLong(1, id);
//            query.executeUpdate();
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    private void objectConstructor(Class<T> cls, ResultSet resultSet, ResultSetMetaData md, T object)
//            throws SQLException, NoSuchFieldException, IllegalAccessException {
//        for (int i = 1; i <= md.getColumnCount(); i++) {
//            String columnName = md.getColumnName(i);
//            Field field = cls.getDeclaredField(columnName);
//            field.setAccessible(true);
//            getColumn(resultSet, object, columnName, field);
//        }
//    }
//
//    private void getColumn(ResultSet resultSet, T client, String columnName, Field field) throws IllegalAccessException, SQLException {
//        if (field.getType().equals(int.class)) {
//            field.set(client, Integer.valueOf(resultSet.getString(columnName)));
//        }
//        if (field.getType().equals(long.class)) {
//            field.set(client, Long.valueOf(resultSet.getString(columnName)));
//        }
//        if (field.getType().equals(String.class)) {
//            field.set(client, resultSet.getString(columnName));
//        }
//    }
//
//    private void setColumnsFromObject(T t, PreparedStatement query, Field[] fields) throws SQLException, IllegalAccessException {
//        for (int i = 1; i < fields.length; i++) {
//            fields[i].setAccessible(true);
//            if (fields[i].getType().equals(long.class)) {
//                query.setLong(i, Long.valueOf(String.valueOf(fields[i].get(t))));
//            } else if (fields[i].getType().equals(int.class)) {
//                query.setInt(i, Integer.valueOf(String.valueOf(fields[i].get(t))));
//            } else {
//                query.setString(i, String.valueOf(fields[i].get(t)));
//            }
//        }
//    }
//
//    private String toStringFields(Field[] fields) {
//        StringBuilder string = new StringBuilder("(");
//        for (int i = 1; i < fields.length; i++) {
//            if (i < fields.length - 1) {
//                string.append(fields[i].getName() + ",");
//            } else {
//                string.append(fields[i].getName());
//            }
//        }
//        string.append(")");
//        return string.toString();
//    }
//
//
//    private String toStringCountParameters(Field[] fields) {
//        StringBuilder string = new StringBuilder("(");
//        for (int i = 1; i < fields.length; i++) {
//            if (i < fields.length - 1) {
//                string.append("?" + ",");
//            } else if (i == fields.length - 1) {
//                string.append("?");
//            }
//        }
//        string.append(")");
//        return string.toString();
//    }




    static final String CAN_NOT_CREATE_ALREADY_SAVED="Can not insert already saved entity (id!=0): ";
    private static final String LOG_MESSAGE_DB_ERROR_CAN_NOT_UPDATE_EMPTY="Can not update null entity ";
    private static final String LOG_MESSAGE_DB_ERROR_CAN_NOT_UPDATE_UNSAVED="Can not update unsaved entity (id==0): ";

    final Connection connection;

    ResultSetExtractor resultSetExtractor;

    AbstractDao(Connection connection) {
        this.connection=connection;
        resultSetExtractor=new ResultSetExtractor();
    }

    protected void checkForNull(Object entity) throws DAOException {
        if (entity == null) {
            throw new DAOException(LOG_MESSAGE_DB_ERROR_CAN_NOT_UPDATE_EMPTY);
        }
    }

    protected void checkIsSaved(Identified entity) throws DAOException {
        if (entity.getId() == 0) {
            throw new DAOException(LOG_MESSAGE_DB_ERROR_CAN_NOT_UPDATE_UNSAVED + entity);
        }
    }

    protected void checkIsUnsaved(Identified entity) throws DAOException {
        if (entity.getId() != 0) {
            throw new DAOException(CAN_NOT_CREATE_ALREADY_SAVED + entity);
        }
    }

    protected Optional<Product> getProduct(int id, String statement) {
        Optional<Product> product=Optional.empty();
        try (PreparedStatement query=connection.prepareStatement(statement)) {
            query.setInt(1, id);
            ResultSet resultSet=query.executeQuery();
            if (resultSet.next()) {
                product=Optional.of(resultSetExtractor.getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + product.toString(), e);
        }
        return product;
    }

    protected Optional<Order> getOrder(int id, String statement) {
        Optional<Order> order=Optional.empty();
        try (PreparedStatement query=connection.prepareStatement(statement)) {
            query.setInt(1, id);
            ResultSet resultSet=query.executeQuery();
            System.out.println(query);
            if (resultSet.next()) {
                order=Optional.of(resultSetExtractor.getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + order.toString(), e);
        }
        return order;
    }

    protected List<User> getUsers(String statement) {
        List<User> users=new ArrayList<>();
        try {
            PreparedStatement query=connection.prepareStatement(statement);
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                users.add(resultSetExtractor.getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION + users.toString(), e);
        }
        return users;
    }
}
