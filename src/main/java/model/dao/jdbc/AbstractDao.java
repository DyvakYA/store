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
