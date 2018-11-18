package model.services.service;

import model.dao.daofactory.DaoManager;
import model.dao.daofactory.JdbcDaoManager;
import model.entities.Order;
import model.entities.UserOrder;
import model.services.UserOrderService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.List;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class UserOrderServiceImpl implements UserOrderService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoManager daoManager = JdbcDaoManager.getInstance();

    private UserOrderServiceImpl(){
    }

    private static class Holder {
        static final UserOrderServiceImpl INSTANCE = new UserOrderServiceImpl();
    }

    public static UserOrderServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<UserOrder> getAll() {

        return transactionHandler.runWithListReturning(connection -> {
            daoManager.createUserOrderDao().findAll();
        });
    }

    public List<Order> getOrdersForUser(int user) {

        return transactionHandler.runWithListReturning(connection -> {
            daoManager.createUserOrderDao().findAllOrdersForUser(user);
        });
    }

    public void create(UserOrder userOrder) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createUserOrderDao().create(userOrder);
        });
    }

    public void update(UserOrder userOrder) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createUserOrderDao().update(userOrder);
        });
    }

    public void delete(int id) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createUserOrderDao().delete(id);
        });
    }
}
