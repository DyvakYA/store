package model.dao.jdbc;

import model.dao.UserDao;
import model.dao.exception.DAOException;
import model.entities.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcUserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final Logger log = Logger.getLogger(JdbcUserDaoImpl.class);

    private static String TABLE = "users";

    public JdbcUserDaoImpl(Connection connection) {
        super(connection, TABLE);
    }

    @Override
    public void create(User user) {
        log.info(user);
        super.create(User.class, user);

    }

    @Override
    public void update(User user) {
        super.update(User.class, user, user.getId());

    }

    @Override
    public void delete(long id) {
        super.delete(id);

    }

    @Override
    public Optional<User> findOne(long id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.info(email);
        Optional<User> user = Optional.empty();
        try (PreparedStatement query = super.connection.prepareStatement("SELECT * FROM users WHERE lower(email) = ?")) {
            query.setString(1, email.toLowerCase());
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(ResultSetExtractor.getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException("sql exception", e);
        }
        log.info(user);
        return user;
    }

    @Override
    public List<User> findAllUsersWithOrders() {
        return null;
    }

    @Override
    public String getPasswordForUser(User user) {
        return null;
    }
}
