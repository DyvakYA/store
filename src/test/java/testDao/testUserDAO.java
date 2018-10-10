package testDao;

import model.dao.DaoConnection;
import model.dao.jdbc.JdbcDaoFactory;
import model.dao.jdbc.JdbcUserDao;
import model.entities.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

/**
 * Created by User on 4/9/2017.
 */
public class TestUserDAO {

    @Test
    public void testGetAll() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        List<User> list;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
            JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
            list = dao.findAll();
        }
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testFindById() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        Optional<User> user;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
            JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
            user = dao.findById(1);
        }
        Assert.assertNotNull(user);
    }

    @Test
    public void testFindByName() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        Optional<User> user;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
            JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
            user = dao.getUserByEmail("user");
        }
        Assert.assertNotNull(user);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        Optional<User> user;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
            JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
            user = dao.getUserByEmail("user");
        }
        Assert.assertNotNull(user);
    }

    @Test
    public void testCreateUser() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        List<User> list1;
        List<User> list2;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
        JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
        list1 = dao.findAll();
        dao.create(new User.Builder()
                .setName("test")
                .setEmail("test")
                .setPasswordHash("test")
                .build());
        list2 = dao.findAll();
    }
        Assert.assertEquals(list1.size(), list2.size()-1 );
}

    @Test
    public void testUpdateUser() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        Optional<User> user1;
        Optional<User> user2;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
            JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
            user1 = dao.getUserByEmail("test");
            dao.update(new User.Builder()
                    .setName("test2")
                    .setEmail("test2")
                    .setPasswordHash("test2")
                    .setAdmin(false)
                    .setBlocked(true)
                    .build(),user1.get().getId());
            user2 = dao.findById(user1.get().getId());
        }
        Assert.assertFalse(user1.equals(user2));
    }

    @Test
    public void testDeleteUser() throws Exception {
        JdbcDaoFactory daoFactory = new JdbcDaoFactory();
        Optional<User> user1;
        Optional<User> user2;
        try (DaoConnection connection = daoFactory.getConnectionForTesting()) {
            JdbcUserDao dao =(JdbcUserDao) daoFactory.createUserDao(connection);
            user1 = dao.getUserByEmail("test2");
            dao.delete(user1.get().getId());
            user2 = dao.getUserByEmail("test2");
        }
        Assert.assertEquals(Optional.empty(), user2);    }
}
