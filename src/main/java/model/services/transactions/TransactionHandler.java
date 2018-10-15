package model.services.transactions;

import model.dao.GenericDao;
import model.dao.daofactory.DaoFactory;
import model.entities.*;

/**
 * Created by User on 5/27/2018.
 */
public interface TransactionHandler {

    void runInTransaction(Transaction transaction);

    void runWithOutCommit(Transaction transaction);

    Object runWithReturnStatement(Transaction transaction);

    GenericDao<Product> createProductDao();

    GenericDao<User> createUserDao();

    GenericDao<Order> createOrderDao();

    GenericDao<UserOrder> createUserOrderDao();

    GenericDao<OrderProduct> createOrderProductDao();

    void setDaoFactory(DaoFactory daoFactory);
}
