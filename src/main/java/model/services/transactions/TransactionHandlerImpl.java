package model.services.transactions;

import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.dao.daofactory.DaoFactory;
import model.entities.*;
import model.services.transactions.exceptions.TransactionException;

import java.sql.Connection;

public class TransactionHandlerImpl implements TransactionHandler {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static class Holder {
        static final TransactionHandlerImpl INSTANCE = new TransactionHandlerImpl();
    }

    public static TransactionHandlerImpl getInstance() {
        return Holder.INSTANCE;
    }


    public void runInTransaction(Transaction transaction) {
        try (DaoConnection dbConnection = daoFactory.getDaoConnection()) {
            dbConnection.beginTransaction();
            transaction.execute(dbConnection);
            dbConnection.commitTransaction();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    public void runWithOutCommit(Transaction transaction) {
        try {
            try (DaoConnection dbConnection = daoFactory.getDaoConnection()) {
                dbConnection.beginTransaction();
                transaction.execute(dbConnection);
                dbConnection.commitTransaction();
            }
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    @Override
    public Object runWithReturnStatement(Transaction transaction) {
        return null;
    }

    public GenericDao<Product> createProductDao() {
        DaoConnection daoConnection = daoFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createProductDao(connection);
    }


    public GenericDao<User> createUserDao() {
        DaoConnection daoConnection = daoFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createUserDao(connection);
    }


    public GenericDao<Order> createOrderDao() {
        DaoConnection daoConnection = daoFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createOrderDao(connection);
    }

    public GenericDao<OrderProduct> createOrderProductDao() {
        DaoConnection daoConnection = daoFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createOrderProductDao(connection);
    }

    public GenericDao<UserOrder> createUserOrderDao() {
        DaoConnection daoConnection = daoFactory.getDaoConnection();
        Connection connection = daoConnection.getConnection();
        return daoFactory.createUserOrderDao(connection);
    }


    public void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

}
