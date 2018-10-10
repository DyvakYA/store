import model.dao.DaoConnection;
import model.dao.jdbc.JdbcDaoConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestDaoConnection {
    @Mock
    private Connection connection;

    private DaoConnection daoConnection;

    @Before
    public void init() throws SQLException{
        daoConnection = new JdbcDaoConnection(connection);
        doNothing().when(connection).commit();
        doNothing().when(connection).rollback();
        doNothing().when(connection).close();
        doNothing().when(connection).setAutoCommit(anyBoolean());
        doNothing().when(connection).setTransactionIsolation(anyInt());
    }

    @Test
    public void testBeginTransaction() throws SQLException{
        daoConnection.beginTransaction();
        verify(connection).setTransactionIsolation(anyInt());
        verify(connection).setAutoCommit(false);
    }

    @Test
    public void testCommitTransaction() throws SQLException{
        daoConnection.commitTransaction();
        verify(connection).setAutoCommit(true);
        verify(connection).commit();
    }

    @Test
    public void testRollBackTransaction() throws SQLException{
        daoConnection.rollbackTransaction();
        verify(connection).rollback();
        verify(connection).setAutoCommit(true);
    }

    @Test
    public void testWholeTransaction() throws SQLException {
        daoConnection.beginTransaction();
        daoConnection.close();
        verify(connection).rollback();
        verify(connection).close();
    }
}