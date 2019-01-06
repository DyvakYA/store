package model.services.transactions;

import model.dao.connection.ConnectionFactory;
import model.dao.connection.DaoConnection;
import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.services.transactions.exceptions.TransactionException;

import java.util.List;
import java.util.Optional;

public class TransactionHandlerImpl implements TransactionHandler {

    private DaoFactory factory = JdbcDaoFactory.getInstance();

    private ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

    private static class Holder {
        static final TransactionHandlerImpl INSTANCE = new TransactionHandlerImpl();
    }

    public static TransactionHandlerImpl getInstance() {
        return Holder.INSTANCE;
    }


    public void runInTransaction(Transaction transaction) {
        try (DaoConnection dbConnection = connectionFactory.getConnection()) {
            factory.setConnection(dbConnection.getConnection());
            dbConnection.beginTransaction();
            transaction.execute(dbConnection);
            dbConnection.commitTransaction();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    public void runWithOutCommit(Transaction transaction) {
        try {
            try (DaoConnection dbConnection = connectionFactory.getConnection()) {
                factory.setConnection(dbConnection.getConnection());
                dbConnection.beginTransaction();
                transaction.execute(dbConnection);
            }
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }
}
