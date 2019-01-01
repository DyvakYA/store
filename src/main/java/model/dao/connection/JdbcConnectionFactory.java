package model.dao.connection;

import model.dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcConnectionFactory extends ConnectionFactory {

    private static final Logger log = Logger.getLogger(JdbcConnectionFactory.class);

    public static final String JAVA_COMP_ENV_JDBC_MYDB = "java:comp/env/jdbc/mydb";

    private DataSource dataSource;

    @Override
    public DaoConnection getConnection() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(JAVA_COMP_ENV_JDBC_MYDB);
            if (dataSource == null) {
                throw new Exception("Data source not found!");
            }
            return new JdbcDaoConnection(dataSource.getConnection());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
