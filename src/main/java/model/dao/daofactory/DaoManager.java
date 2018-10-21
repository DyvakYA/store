package model.dao.daofactory;

import model.dao.GenericDao;
import model.entities.*;

public interface DaoManager {

    GenericDao<Product> createProductDao();

    GenericDao<User> createUserDao();

    GenericDao<Order> createOrderDao();

    GenericDao<UserOrder> createUserOrderDao();

    GenericDao<OrderProduct> createOrderProductDao();

    void setDaoFactory(DaoFactory daoFactory);
}
