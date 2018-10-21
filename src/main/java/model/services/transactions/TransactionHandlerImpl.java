package model.services.transactions;

import model.dao.GenericDao;
import model.dao.connection.DaoConnection;
import model.dao.connection.DaoConnectionFactory;
import model.dao.connection.JdbcDaoConnectionFactory;
import model.dao.daofactory.DaoFactory;
import model.entities.*;
import model.services.transactions.exceptions.TransactionException;

import java.sql.Connection;

public class TransactionHandlerImpl implements TransactionHandler {



    private DaoConnectionFactory connectionFactory = new JdbcDaoConnectionFactory();

    private static class Holder {
        static final TransactionHandlerImpl INSTANCE = new TransactionHandlerImpl();
    }

    public static TransactionHandlerImpl getInstance() {
        return Holder.INSTANCE;
    }


    public void runInTransaction(Transaction transaction) {
        try (DaoConnection dbConnection = connectionFactory.getDaoConnection()) {
            dbConnection.beginTransaction();
            transaction.execute(dbConnection);
            dbConnection.commitTransaction();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    public void runWithOutCommit(Transaction transaction) {
        try {
            try (DaoConnection dbConnection = connectionFactory.getDaoConnection()) {
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



}
