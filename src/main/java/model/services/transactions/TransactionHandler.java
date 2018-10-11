package model.services.transactions;

import model.dao.daofactory.DaoFactory;
import model.dao.GenericDao;
import model.entities.Order;
import model.entities.Product;
import model.entities.User;

/**
 * Created by User on 5/27/2018.
 */
public interface TransactionHandler {

    void runInTransaction(Transaction transaction);

    void runWithOutCommit(Transaction transaction);

    GenericDao<Product> createProductDao();

    GenericDao<User> createUserDao();

    GenericDao<Order> createOrderDao();

    void setDaoFactory(DaoFactory daoFactory);
}
