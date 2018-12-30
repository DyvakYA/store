package model.dao.jdbc;

import model.dao.GenericDao;
import model.dao.UserDao;
import model.entities.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcUserDaoImpl extends AbstractDao<User> implements UserDao {

    private static String TABLE = "users";

    public JdbcUserDaoImpl(Connection connection) {
        super(connection, TABLE);
    }

    @Override
    public void create(User user) {
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
    public User findOne(long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.empty();
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
