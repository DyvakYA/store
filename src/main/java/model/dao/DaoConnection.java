package model.dao;

/**
 * This class represents DaoManager pattern.
 * It encapsulates native connection and DAO factory.
 * <p>
 * It should consider transaction's rollback necessary in close method.
 * It MUST call ROLLBACK IF TRANSACTION has been begun
 * but was NOT COMMITTED before close method was called.
 *
 * @author dyvakyurii@gmail.com
 */
public interface DaoConnection extends AutoCloseable {

    /**
     * Defines begin of transaction
     */
    void beginTransaction();

    /**
     * Defines begin of transaction with high isolation level
     */
    void beginSerializableTransaction();

    /**
     * Saves transaction.
     */
    void commitTransaction();

    /**
     * rolls back transaction
     */
    void rollbackTransaction();

    /**
     * Closes connection.
     * <p>
     * IMPORTANT.
     * It MUST call ROLLBACK IF TRANSACTION has been begun
     * but was NOT COMMITTED before close method was called, f.e. if any exception was thrown
     */
    @Override
    void close();
}
