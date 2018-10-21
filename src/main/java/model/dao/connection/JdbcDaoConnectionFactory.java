package model.dao.connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JdbcDaoConnectionFactory implements DaoConnectionFactory {

    public static final String JAVA_COMP_ENV_JDBC_MYDB = "java:comp/env/jdbc/mydb";

    private DataSource dataSource;

    public JdbcDaoConnectionFactory() {

    }

    @Override
    public DaoConnection getDaoConnection() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup(JAVA_COMP_ENV_JDBC_MYDB);
            return new JdbcDaoConnection(dataSource.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
