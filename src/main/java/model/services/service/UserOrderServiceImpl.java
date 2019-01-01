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
import java.util.concurrent.atomic.AtomicReference;

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

        AtomicReference<List<UserOrder>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithListReturning(connection -> {
            result.set(daoFactory.createUserOrderDao().findAll());
        });
        return result.get();
    }

    public List<Order> getOrdersForUser(int user) {

        AtomicReference<List<Order>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithListReturning(connection -> {
            result.set(daoFactory.createUserOrderDao().findAllOrdersForUser(user));
        });
        return result.get();
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
