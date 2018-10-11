package model.dao.connection;

import model.services.transactions.exceptions.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

/**
 * Created by User on 5/25/2018.
 */
public class JdbcDaoConnection implements DaoConnection {

    private static final String CAN_NOT_BEGIN_TRANSACTION = "Can not begin transaction";
    private static final String CAN_NOT_CLOSE_CONNECTION = "Can not close transaction";
    private static final String CAN_NOT_COMMIT_TRANSACTION = "Can not commit transaction";
    private static final String CAN_NOT_ROLLBACK_TRANSACTION = "Can not rollback transaction";

    private Connection connection;
    private boolean inTransaction = false;

    public JdbcDaoConnection(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public void beginTransaction() {
        try {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            inTransaction = true;
        } catch (SQLException e) {
            throw new TransactionException(CAN_NOT_BEGIN_TRANSACTION, e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            inTransaction = false;
        } catch (SQLException e) {
            throw new TransactionException(CAN_NOT_COMMIT_TRANSACTION, e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            inTransaction = false;
        } catch (SQLException e) {
            throw new TransactionException(CAN_NOT_ROLLBACK_TRANSACTION, e);
        }
    }

    @Override
    public void close() {
        if (inTransaction) {
            rollbackTransaction();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(CAN_NOT_CLOSE_CONNECTION, e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

