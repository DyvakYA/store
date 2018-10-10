package model.services.service;

import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.UserOrderDao;
import model.entities.Order;
import model.entities.UserOrder;
import model.services.UserOrderService;

import java.util.List;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class UserOrderServiceImpl implements UserOrderService {

    private DaoFactory daoFactory=DaoFactory.getInstance();

    private static class Holder {
        static final UserOrderServiceImpl INSTANCE=new UserOrderServiceImpl();
    }

    public static UserOrderServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<UserOrder> getAll() {
        try (DaoConnection connection=daoFactory.getConnection()) {
            connection.beginTransaction();
            UserOrderDao userOrderDao=daoFactory.createUserOrderDao(connection);
            return userOrderDao.findAll();
        }
    }

    public List<Order> getOrdersForUser(int user) {
        DaoConnection connection=daoFactory.getConnection();
        connection.beginTransaction();
        UserOrderDao userOrderDao=daoFactory.createUserOrderDao(connection);
        return userOrderDao.findAllOrdersForUser(user);
    }

     public void create(UserOrder userOrder) {
        try (DaoConnection connection=daoFactory.getConnection()) {
            connection.beginTransaction();
            UserOrderDao userOrderDao=daoFactory.createUserOrderDao(connection);
            userOrderDao.create(userOrder);
            connection.commitTransaction();
        }
    }

    public void update(UserOrder userOrder) {
        try (DaoConnection connection=daoFactory.getConnection()) {
            connection.beginTransaction();
            UserOrderDao userOrderDao=daoFactory.createUserOrderDao(connection);
            userOrderDao.update(userOrder, userOrder.getId());
            connection.commitTransaction();
        }
    }

    public void delete(int id) {
        try (DaoConnection connection=daoFactory.getConnection()) {
            connection.beginTransaction();
            UserOrderDao transportDao=daoFactory.createUserOrderDao(connection);
            transportDao.delete(id);
            connection.commitTransaction();
        }
    }
}
