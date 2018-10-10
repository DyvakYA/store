package testService;

import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entities.User;
import model.services.exception.ServiceException;
import model.services.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by User on 4/24/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

    @Mock
    private DaoFactory daoFactory;

    @Mock
    private DaoConnection daoConnection;

    @Mock
    private UserDao userDao;

    private UserServiceImpl userServiceImpl;
    private User user1;
    private User user2;

    @Before
    public void init() {

        user1=new User.Builder()
                .setId(1)
                .setName("John")
                .setEmail("John@gmail.com")
                .setAdmin(true)
                .setBlocked(false)
                .build();

        user2=new User.Builder()
                .setId(2)
                .setName("Mike")
                .setEmail("Mike@gmail.com")
                .setAdmin(false)
                .setBlocked(true)
                .build();

        when(daoFactory.getConnection()).thenReturn(daoConnection);
        when(daoFactory.createUserDao(daoConnection)).thenReturn(userDao);
        userServiceImpl=new UserServiceImpl();
        (userServiceImpl).setDaoFactory(daoFactory);
        when(userDao.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(userDao.getUserByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        doNothing().when(userDao).create(any(User.class));
        when(userDao.findAll()).thenReturn(Arrays.asList(user1, user2));
    }

    @Test
    public void testFindByID() {
        Optional<User> user=userServiceImpl.getById(1);
        Assert.assertNotNull(user.get());
        Assert.assertEquals(1, user.get().getId());
        verify(userDao, times(1)).findById(1);
    }

    @Test(expected = ServiceException.class)
    public void testLoginWithWrongCredentials(){
        String wrongPassword="wrong password";
        userServiceImpl.login(user1.getEmail(), wrongPassword);
        verify(userDao, times(1)).getUserByEmail(user1.getEmail());
    }

    @Test(expected = ServiceException.class)
    public void testCreateUserWithExistingEmail(){
        User user = new User.Builder().setEmail("John@gmail.com").build();
        userServiceImpl.create(user);
    }

    @Test
    public void testCreateSerWithCorrectCredentials(){
        String email = "Nick@gmail.com";
        User user3=new User.Builder()
                .setName("Nick")
                .setEmail("Nick@gmail.com")
                .setAdmin(false)
                .setBlocked(true)
                .build();
        when(userDao.getUserByEmail(email)).thenReturn(Optional.empty());
        userServiceImpl.create(user3);
        verify(userDao, times(1)).getUserByEmail(email);
        verify(userDao, times(1)).create(user3);
    }

    @Test
    public void testFindAll(){
        List<User> users = userServiceImpl.getAll();
        Assert.assertEquals(2, users.size());
        verify(userDao, times(1)).findAll();
    }

    @Test
    public void testUpdateUserDescription(){
        userServiceImpl.update(user1);
        doNothing().when(userDao).update(user1, user1.getId());
        verify(userDao, times(1)).update(user1, user1.getId());
    }

    @Test
    public void testCreateUser() {
        doNothing().when(userDao).create(any());
        when(userDao.getUserByEmail(user1.getEmail())).thenReturn(Optional.empty());
        userServiceImpl.create(user1);
        verify(userDao, times(1)).create(user1);
    }
}


