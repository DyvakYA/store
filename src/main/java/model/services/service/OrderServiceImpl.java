package model.services.service;

import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.entities.Order;
import model.services.OrderService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static model.constants.AttributesHolder.STARTED;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class OrderServiceImpl implements OrderService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoFactory daoFactory = JdbcDaoFactory.getInstance();


    private OrderServiceImpl() {

    }

    private static class Holder {
        static final OrderServiceImpl INSTANCE = new OrderServiceImpl();
    }

    public static OrderServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<Order> getAll() {
        AtomicReference<List<Order>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory.createOrderDao().findAll());
        });
        return result.get();
    }

    public Order createDefaultOrder() {

        Order order = Order.builder()
                .setOrderStatus(STARTED)
                .setDate(new Date())
                .build();
        transactionHandler.runInTransaction(connection -> {
            daoFactory.createOrderDao().create(order);
        });
        return order;
    }

    @Override
    public void updateOrderStatus(Order order) {

        transactionHandler.runInTransaction(connection -> {
            daoFactory.createOrderDao().update(order);
        });
    }

    public void create(Order order) {
        transactionHandler.runInTransaction(connection -> {
            daoFactory
                    .createOrderDao()
                    .create(order);
        });
    }

    public void update(Order order) {
        transactionHandler.runInTransaction(connection -> {
            daoFactory
                    .createOrderDao()
                    .update(order);
        });
    }

    public void delete(int id) {
        transactionHandler.runWithOutCommit(connection -> {
            daoFactory
                    .createOrderDao()
                    .delete(id);
        });
    }

}
