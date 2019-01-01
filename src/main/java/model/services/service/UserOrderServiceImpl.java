package model.services.service;

import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.entities.Order;
import model.entities.UserOrder;
import model.services.UserOrderService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class UserOrderServiceImpl implements UserOrderService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoFactory daoFactory = JdbcDaoFactory.getInstance();

    private UserOrderServiceImpl() {
    }

    private static class Holder {
        static final UserOrderServiceImpl INSTANCE = new UserOrderServiceImpl();
    }

    public static UserOrderServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<UserOrder> getAll() {

        transactionHandler.runWithListReturning(connection -> {
            return daoFactory.createUserOrderDao().findAll();
        });
        return Collections.emptyList();
    }

    public List<Order> getOrdersForUser(int user) {

        transactionHandler.runWithListReturning(connection -> {
            return daoFactory.createUserOrderDao().findAllOrdersForUser(user);
        });
        return Collections.emptyList();
    }

    public void create(UserOrder userOrder) {

        transactionHandler.runInTransaction(connection -> {
            daoFactory.createUserOrderDao().create(userOrder);
        });
    }

    public void update(UserOrder userOrder) {

        transactionHandler.runInTransaction(connection -> {
            daoFactory.createUserOrderDao().update(userOrder);
        });
    }

    public void delete(int id) {

        transactionHandler.runInTransaction(connection -> {
            daoFactory.createUserOrderDao().delete(id);
        });
    }
}
