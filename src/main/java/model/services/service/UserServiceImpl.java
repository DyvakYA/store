package model.services.service;

import model.dao.daofactory.DaoManager;
import model.dao.daofactory.JdbcDaoManager;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.services.UserService;
import model.services.exception.ServiceException;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static model.services.exception.ServiceException.USER_ALREADY_EXISTS;

public class UserServiceImpl implements UserService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoManager daoManager = JdbcDaoManager.getInstance();

    private UserServiceImpl(){

    }

    private static class Holder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<User> login(String email, String password) {
        return transactionHandler.runWithReturnStatement(connection -> {
            daoManager
                    .createUserDao()
                    .getUserByEmail(email)
                    .filter(user -> (user.calcPasswordHash(password)).equals(user.getPassword()))
                    .orElseThrow(() -> new ServiceException(USER_ALREADY_EXISTS));
        });
    }

    public List<User> getAll() {
        return transactionHandler.runWithListReturning(connection -> {
            daoManager
                    .createUserDao()
                    .findAll();
        });
    }


    public List<User> getAllUsersWithOrders() {

        return transactionHandler.runWithListReturning(connection -> {
            daoManager
                    .createUserDao()
                    .findAllUsersWithOrders();
        });
    }

    public Optional<User> getByEmail(String email) {

        return transactionHandler.runWithReturnStatement(connection -> {
            daoManager.createUserDao().getUserByEmail(email);
        });
    }

    public Optional<User> getById(int id) {

        return transactionHandler.runWithReturnStatement(connection -> {
            daoManager.createUserDao().findOne(id);
        });
    }

    public Map<User, Map<Order, Map<OrderProduct, Product>>> getUserMap(List<User> users) {

        Map<User, Map<Order, Map<OrderProduct, Product>>> result = new HashMap<>();

        transactionHandler.runWithOutCommit(connection -> {

            for (User user : users) {

                List<Order> orders = daoManager
                        .createUserOrderDao()
                        .findAllOrdersForUser(user.getId());

                Map<Order, Map<OrderProduct, Product>> orderMap = new HashMap<>();

                for (Order order : orders) {
                    List<OrderProduct> orderProducts = daoManager
                            .createOrderProductDao()
                            .findOrderProductsByOrderId(order.getId());

                    Map<OrderProduct, Product> orderProductMap = new HashMap<>();

                    for (OrderProduct orderProduct : orderProducts) {

                        Optional<Product> optionalProduct = daoManager
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
            daoManager
                    .createUserDao()
                    .create(user);
        });
    }


    public void update(User user) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createUserDao()
                    .update(user);
        });
    }

    public void delete(int id) {

        transactionHandler.runWithOutCommit(connection -> {
            daoManager
                    .createUserDao()
                    .delete(id);
        });
    }
}
