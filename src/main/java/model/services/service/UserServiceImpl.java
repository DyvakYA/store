package model.services.service;

import model.dao.OrderProductDao;
import model.dao.UserDao;
import model.dao.UserOrderDao;
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

    private static class Holder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<User> login(String email, String password) {
        return transactionHandler.runWithOutCommit(connection -> {
            transactionHandler
                    .createUserDao()
                    .getUserByEmail(email)
                    .filter(user -> (user.calcPasswordHash(password)).equals(user.getPasswordHash()))
                    .orElseThrow(() -> new ServiceException(USER_ALREADY_EXISTS));
        });
    }

    public List<User> getAll() {
        return (List<User>) transactionHandler.runWithReturnStatement(connection -> {
            transactionHandler
                    .createUserDao()
                    .findAll();
        });
    }


    public List<User> getAllUsersWithOrders() {

        return transactionHandler.runWithOutCommit(connection -> {
            transactionHandler
                    .createUserDao()
                    .findAllUsersWithOrder();
        });
    }

    public Optional<User> getByEmail(String email) {

        return transactionHandler.runWithOutCommit(connection -> {
            transactionHandler.createUserDao().getUserByEmail();
        });
    }

    public Optional<User> getById(int id) {

        return transactionHandler.runWithOutCommit(connection -> {
            transactionHandler.createUserDao().findOne(id);
        });
    }

    public Map<User, Map<Order, Map<OrderProduct, Product>>> getUserMap(List<User> userList) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            UserOrderDao userOrderDao = daoFactory.createUserOrderDao(connection);
            Map<User, Map<Order, Map<OrderProduct, Product>>> userMap = new HashMap<>();
            for (User user : userList) {
                List<Order> ordersList = userOrderDao.findAllOrdersForUser(user.getId());
                Map<Order, Map<OrderProduct, Product>> orderMap = new HashMap<>();
                for (Order order : ordersList) {
                    List<OrderProduct> orderProductList = orderProductDao.findOrderProductsByOrderId(order.getId());
                    Map<OrderProduct, Product> orderProductMap = new HashMap<>();
                    for (OrderProduct orderProduct : orderProductList) {
                        Optional<Product> optionalProduct = orderProductDao.findProductByOrderProductId(orderProduct.getId());
                        if (optionalProduct.isPresent()) {
                            Product product = optionalProduct.get();
                            orderProductMap.put(orderProduct, product);
                        }
                    }
                    orderMap.put(order, orderProductMap);
                }
                userMap.put(user, orderMap);
            }
            return userMap;
        }
    }

    public void create(User user) {
        transactionHandler.runInTransaction(connection -> {
            transactionHandler
                    .createUserDao()
                    .create(user);
        });
    }


    public void update(User user) {

        transactionHandler.runInTransaction(connection -> {
            transactionHandler.createUserDao()
                    .update(user);
        });
    }

    public void delete(int id) {

        transactionHandler.runWithOutCommit(connection -> {
            transactionHandler
                    .createUserDao()
                    .delete(id);
        });
    }

    private void checkIfUserAlreadyExists(String email, UserDao userDao) {
        if (userDao.getUserByEmail(email).isPresent()) {
            throw new ServiceException(USER_ALREADY_EXISTS);
        }
    }
}
