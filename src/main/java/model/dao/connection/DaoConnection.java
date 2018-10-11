package model.dao.connection;

import java.sql.Connection;

/**
 * Created by User on 5/25/2018.
 */
public interface DaoConnection extends AutoCloseable {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();

    Connection getConnection();
}
