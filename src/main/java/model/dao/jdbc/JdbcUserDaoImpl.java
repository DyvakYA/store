package model.dao.jdbc;

import model.dao.GenericDao;
import model.entities.User;

import java.sql.Connection;
import java.util.List;

public class JdbcUserDaoImpl extends AbstractDao<User> implements GenericDao<User> {

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
}
