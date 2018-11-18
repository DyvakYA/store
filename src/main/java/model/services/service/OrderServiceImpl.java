package model.services.service;

import model.dao.daofactory.DaoManager;
import model.dao.daofactory.JdbcDaoManager;
import model.entities.Order;
import model.services.OrderService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.Date;
import java.util.List;

import static model.constants.AttributesHolder.STARTED;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class OrderServiceImpl implements OrderService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoManager daoManager = JdbcDaoManager.getInstance();


    private OrderServiceImpl() {

    }

    private static class Holder {
        static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    }

    public static OrderServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<Order> getAll() {
        return transactionHandler.runWithListReturning(connection -> {
            daoManager.createOrderDao().findAll();
        });
    }

    public Order createDefaultOrder() {

        Order order = Order.builder()
                .setOrderStatus(STARTED)
                .setDate(new Date())
                .build();
        transactionHandler.runWithReturnStatement(connection -> {
            daoManager.createOrderDao().create(order);
        });
        return order;
    }

    @Override
    public void updateOrderStatus(Order order) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createOrderDao().update(order);
        });
    }

    public void create(Order order) {
        transactionHandler.runInTransaction(connection -> {
            daoManager
                    .createOrderDao()
                    .create(order);
        });
    }

    public void update(Order order) {
        transactionHandler.runInTransaction(connection -> {
            daoManager
                    .createOrderDao()
                    .update(order);
        });
    }

    public void delete(int id) {
        transactionHandler.runWithOutCommit(connection -> {
            daoManager
                    .createOrderDao()
                    .delete(id);
        });
    }

}
