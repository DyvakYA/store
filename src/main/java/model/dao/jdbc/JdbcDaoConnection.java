package model.dao.jdbc;

import model.dao.DaoConnection;
import model.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

/**
 * This class represents JDBC implementation of DaoConnection.
 * It performs rollback if transaction began but was not committed or rolled back before close method was called.
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcDaoConnection implements DaoConnection {

    private static final String CAN_NOT_BEGIN_TRANSACTION="Can not begin transaction";
    private static final String CAN_NOT_CLOSE_CONNECTION="Can not close transaction";
    private static final String CAN_NOT_COMMIT_TRANSACTION="Can not commit transaction";
    private static final String CAN_NOT_ROLLBACK_TRANSACTION="Can not rollback transaction";

    private Connection connection;
    private boolean inTransaction=false;

    public JdbcDaoConnection(Connection connection) {
        super();
        this.connection=connection;
    }

    @Override
    public void beginTransaction() {
        beginTransactionWithIsolationLevel(TRANSACTION_READ_COMMITTED);
    }

    @Override
    public void beginSerializableTransaction() {
        beginTransactionWithIsolationLevel(TRANSACTION_SERIALIZABLE);
    }

    private void beginTransactionWithIsolationLevel(int transactionIsolationLevel) {
        try {
            connection.setTransactionIsolation(transactionIsolationLevel);
            connection.setAutoCommit(false);
            inTransaction=true;
        } catch (SQLException e) {
            throw new DAOException(CAN_NOT_BEGIN_TRANSACTION, e);
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
            throw new DAOException(CAN_NOT_CLOSE_CONNECTION, e);
        }
    }

    @Override
    public void commitTransaction() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            inTransaction=false;
        } catch (SQLException e) {
            throw new DAOException(CAN_NOT_COMMIT_TRANSACTION, e);
        }
    }

    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            inTransaction=false;
        } catch (SQLException e) {
            throw new DAOException(CAN_NOT_ROLLBACK_TRANSACTION, e);
        }

    }

    Connection getConnection() {
        return connection;
    }
}
