package model.services.service;

import model.dao.OrderDao;
import model.dao.OrderProductDao;
import model.dao.connection.DaoConnection;
import model.dao.daofactory.DaoManager;
import model.dao.daofactory.JdbcDaoManager;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.UserOrder;
import model.services.OrderProductService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class OrderProductServiceImpl implements OrderProductService {

    private static class Holder {
        static final OrderProductServiceImpl INSTANCE = new OrderProductServiceImpl();
    }

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();

    private DaoManager daoManager = new JdbcDaoManager();

    public static OrderProductServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<OrderProduct> getAll() {
        return (List<OrderProduct>) transactionHandler.runWithReturnStatement(connection -> {
            daoManager
                    .createOrderProductDao()
                    .findAll();
        });
    }

    public void createUserOrderAndOrderProduct(int userId, int orderId, int productId, int quantity) {

        transactionHandler.runInTransaction(connection -> {

            UserOrder userOrder = UserOrder.builder()
                    .setUserId(userId)
                    .setOrderId(orderId)
                    .build();
            daoManager.createUserOrderDao().create(userOrder);

            OrderProduct orderProduct = OrderProduct.builder()
                    .setOrderId(orderId)
                    .setProductId(productId)
                    .setQuantity(quantity)
                    .build();

            long productPrice = daoManager
                    .createOrderProductDao()
                    .getProductPrice(orderProduct);

            orderProduct.setProductSum((long) orderProduct.getQuantity() *
                    productPrice);

            daoManager
                    .createOrderProductDao()
                    .create(orderProduct);


            //setOrderTotalPrice(orderProduct, connection, orderProductDao);
        });
    }

    public void create(OrderProduct orderProduct) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            orderProduct.setProductSum((long) orderProduct.getQuantity() *
                    orderProductDao.getProductPrice(orderProduct));
            orderProductDao.create(orderProduct);
            setOrderTotalPrice(orderProduct, connection, orderProductDao);
            connection.commitTransaction();


        }
    }

    public void increaseQuantityWhenAddProduct(OrderProduct orderProduct, int quantity) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            orderProduct.setQuantity(orderProduct.getQuantity() + quantity);
            orderProduct.setProductSum((long) orderProduct.getQuantity() * orderProductDao.getProductPrice(orderProduct));
            orderProductDao.update(orderProduct, orderProduct.getId());
            setOrderTotalPrice(orderProduct, connection, orderProductDao);
            connection.commitTransaction();
        }
    }

    public void update(OrderProduct orderProduct) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            orderProduct.setProductSum((long) orderProduct.getQuantity() * orderProductDao.getProductPrice(orderProduct));
            orderProductDao.update(orderProduct, orderProduct.getId());
            setOrderTotalPrice(orderProduct, connection, orderProductDao);
            connection.commitTransaction();
        }
    }

    private void setOrderTotalPrice(OrderProduct orderProduct, DaoConnection connection, OrderProductDao orderProductDao) {
        Optional<Order> optionalOrder = orderProductDao.findOrderByOrderProductId(orderProduct.getId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setTotalPrice(orderProductDao.getOrderTotalPrice(order));
            OrderDao orderDao = daoFactory.createOrderDao(connection);
            orderDao.update(order, order.getId());
        }

    }

    public void delete(int id) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createOrderProductDao().delete(id);
        });
    }

    public Optional<OrderProduct> findById(int id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            return orderProductDao.findById(id);
        }
    }

    public Optional<OrderProduct> getOrderProductByOrderIdAndProductId(int orderId, int productId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            return orderProductDao.findOrderProductByOrderIdAndProductId(orderId, productId);
        }
    }

    public void deleteProductFromOrder(int orderId, int productId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProduct orderProduct = null;
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            Optional<OrderProduct> optionalOrderProduct = orderProductDao.findOrderProductByOrderIdAndProductId(orderId, productId);
            if (optionalOrderProduct.isPresent()) {
                orderProduct = optionalOrderProduct.get();
            }
            Optional<Order> optionalOrder = orderProductDao.findOrderByOrderProductId(orderProduct.getId());
            orderProductDao.deleteProductFromOrder(orderId, productId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setTotalPrice(orderProductDao.getOrderTotalPrice(order));
                OrderDao orderDao = daoFactory.createOrderDao(connection);
                orderDao.update(order, order.getId());
            }
            connection.commitTransaction();
        }
    }

    public Map<Order, Map<OrderProduct, Product>> getOrdersMap(List<Order> orders) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            OrderProductDao orderProductDao = daoFactory.createOrderProductDao(connection);
            Map<Order, Map<OrderProduct, Product>> orderMap = new HashMap<>();
            for (Order order : orders) {
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
            return orderMap;
        }
    }
}
