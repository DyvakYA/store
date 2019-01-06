package model.services.service;

import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.services.UserService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

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

        AtomicReference<Optional<User>> result = new AtomicReference<>(Optional.empty());
        transactionHandler.runWithOutCommit(connection -> {

            User superUser = daoFactory.createUserDao().getUserByEmail(email).get();
            log.info("RESULT" + superUser.calcPasswordHash(password)+ " - " +superUser.getPassword());

            log.info(daoFactory.createUserDao().getUserByEmail(email).filter(user -> (user.calcPasswordHash(password)).equals(user.getPassword())));

            result.set(daoFactory
                    .createUserDao()
                    .getUserByEmail(email)
                    .filter(user -> (user.calcPasswordHash(password)).equals(user.getPassword())));
            // .orElseThrow(() -> new ServiceException(USER_ALREADY_EXISTS)));
        });
        log.info(result.get());
        return result.get();
    }

    public List<User> getAll() {

        AtomicReference<List<User>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory
                    .createUserDao()
                    .findAll());
        });
        return result.get();
    }


    public List<User> getAllUsersWithOrders() {

        AtomicReference<List<User>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory
                    .createUserDao()
                    .findAllUsersWithOrders());
        });
        return result.get();
    }

    public Optional<User> getByEmail(String email) {
        AtomicReference<Optional<User>> result = new AtomicReference<>(Optional.empty());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory.createUserDao().getUserByEmail(email));
        });
        return result.get();
    }

    public Optional<User> getById(int id) {

        AtomicReference<Optional<User>> result = new AtomicReference<>(Optional.empty());
        transactionHandler.runInTransaction(connection -> {
            result.set(daoFactory.createUserDao().findOne(id));
        });
        return result.get();
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
