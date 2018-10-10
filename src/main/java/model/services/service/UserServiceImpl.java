package model.services.service;

import model.dao.*;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.services.UserService;
import model.services.exception.ServiceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static model.services.exception.ServiceException.USER_ALREADY_EXISTS;

public class UserServiceImpl implements UserService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    public void setDaoFactory(DaoFactory daoFactory) {
        this.daoFactory=daoFactory;
    }

    private static class Holder {
        static final UserServiceImpl INSTANCE = new UserServiceImpl();
    }

    public static UserServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public Optional<User> login(String email, String password) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            return Optional.ofNullable(userDao.getUserByEmail(email)
                    .filter(user -> (user.calcPasswordHash(password)).equals(user.getPasswordHash()))
                    .orElseThrow(() -> new ServiceException(USER_ALREADY_EXISTS)));
        }
    }

    public List<User> getAll() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            return userDao.findAll();
        }
    }

    public List<User> getAllUsersWithOrders() {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            return userDao.findAllUsersWithOrders();
        }
    }

    public Optional<User> getByEmail(String email) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            return userDao.getUserByEmail(email);
        }
    }

    public Optional<User> getById(int id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            return userDao.findById(id);
        }
    }

    public Map<User,Map<Order,Map<OrderProduct,Product>>> getUserMap(List<User> userList) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao=daoFactory.createOrderProductDao(connection);
            UserOrderDao userOrderDao=daoFactory.createUserOrderDao(connection);
            Map<User,Map<Order,Map<OrderProduct,Product>>> userMap = new HashMap<>();
            for(User user : userList) {
                List<Order> ordersList=userOrderDao.findAllOrdersForUser(user.getId());
                Map<Order, Map<OrderProduct, Product>> orderMap=new HashMap<>();
                for (Order order : ordersList) {
                    List<OrderProduct> orderProductList=orderProductDao.findOrderProductsByOrderId(order.getId());
                    Map<OrderProduct, Product> orderProductMap=new HashMap<>();
                    for (OrderProduct orderProduct : orderProductList) {
                        Optional<Product> optionalProduct=orderProductDao.findProductByOrderProductId(orderProduct.getId());
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
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            checkIfUserAlreadyExists(user.getEmail(), userDao);
            userDao.create(user);
            connection.commitTransaction();
        }
    }

    public void update(User user) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            user.setPasswordHash(userDao.getPasswordForUser(user));
            userDao.update(user, user.getId());
            connection.commitTransaction();
        }
    }

    public void delete(int id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            UserDao userDao=daoFactory.createUserDao(connection);
            userDao.delete(id);
            connection.commitTransaction();
        }
    }

    private void checkIfUserAlreadyExists(String email, UserDao userDao){
        if(userDao.getUserByEmail(email).isPresent()){
            throw new ServiceException(USER_ALREADY_EXISTS);
        }
    }
}
