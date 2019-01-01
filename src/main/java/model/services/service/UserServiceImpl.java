package model.services.service;

import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.services.UserService;
import model.services.exception.ServiceException;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.*;

import static model.services.exception.ServiceException.USER_ALREADY_EXISTS;

public class UserServiceImpl implements UserService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoFactory daoFactory = JdbcDaoFactory.getInstance();

    private UserServiceImpl() {

    }

    private static class Holder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<User> login(String email, String password) {
        return transactionHandler.runWithReturnStatement(connection -> {
            daoFactory
                    .createUserDao()
                    .getUserByEmail(email)
                    .filter(user -> (user.calcPasswordHash(password)).equals(user.getPassword()))
                    .orElseThrow(() -> new ServiceException(USER_ALREADY_EXISTS));
        });
    }

    public List<User> getAll() {
        transactionHandler.runWithListReturning(connection -> {
            return daoFactory
                    .createUserDao()
                    .findAll();
        });
        return Collections.emptyList();
    }


    public List<User> getAllUsersWithOrders() {

        transactionHandler.runWithListReturning(connection -> {
            return daoFactory
                    .createUserDao()
                    .findAllUsersWithOrders();
        });
        return Collections.emptyList();
    }

    public Optional<User> getByEmail(String email) {

        return transactionHandler.runWithReturnStatement(connection -> {
            daoFactory.createUserDao().getUserByEmail(email);
        });
    }

    public Optional<User> getById(int id) {

        return transactionHandler.runWithReturnStatement(connection -> {
            daoFactory.createUserDao().findOne(id);
        });
    }

    public Map<User, Map<Order, Map<OrderProduct, Product>>> getUserMap(List<User> users) {

        Map<User, Map<Order, Map<OrderProduct, Product>>> result = new HashMap<>();

        transactionHandler.runWithOutCommit(connection -> {

            for (User user : users) {

                List<Order> orders = daoFactory
                        .createUserOrderDao()
                        .findAllOrdersForUser(user.getId());

                Map<Order, Map<OrderProduct, Product>> orderMap = new HashMap<>();

                for (Order order : orders) {
                    List<OrderProduct> orderProducts = daoFactory
                            .createOrderProductDao()
                            .findOrderProductsByOrderId(order.getId());

                    Map<OrderProduct, Product> orderProductMap = new HashMap<>();

                    for (OrderProduct orderProduct : orderProducts) {

                        Optional<Product> optionalProduct = daoFactory
                                .createOrderProductDao()
                                .findProductByOrderProductId(orderProduct.getId());

                        if (optionalProduct.isPresent()) {
                            Product product = optionalProduct.get();
                            orderProductMap.put(orderProduct, product);
                        }
                    }

                    orderMap.put(order, orderProductMap);


                }
                result.put(user, orderMap);
            }
        });
        return result;
    }

    public void create(User user) {
        transactionHandler.runInTransaction(connection -> {
            daoFactory
                    .createUserDao()
                    .create(user);
        });
    }


    public void update(User user) {

        transactionHandler.runInTransaction(connection -> {
            daoFactory.createUserDao()
                    .update(user);
        });
    }

    public void delete(int id) {

        transactionHandler.runWithOutCommit(connection -> {
            daoFactory
                    .createUserDao()
                    .delete(id);
        });
    }
}
