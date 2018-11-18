package model.services.transactions;

import model.dao.connection.DaoConnection;
import model.dao.connection.DaoConnectionFactory;
import model.dao.connection.JdbcDaoConnectionFactory;
import model.services.transactions.exceptions.TransactionException;

import java.util.List;
import java.util.Optional;

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
    public Optional runWithReturnStatement(Transaction transaction) {
        return null;
    }

    @Override
    public List runWithListReturning(Transaction transaction) {
        return null;
    }


}
