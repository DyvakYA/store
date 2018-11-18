package model.services.service;

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

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoManager daoManager = JdbcDaoManager.getInstance();

    private OrderProductServiceImpl(){

    }

    private static class Holder {
        static final OrderProductServiceImpl INSTANCE = new OrderProductServiceImpl();
    }

    public static OrderProductServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<OrderProduct> getAll() {
        return transactionHandler.runWithListReturning(connection -> {
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

            daoManager
                    .createUserOrderDao()
                    .create(userOrder);

            OrderProduct orderProduct = OrderProduct.builder()
                    .setOrderId(orderId)
                    .setProductId(productId)
                    .setQuantity(quantity)
                    .build();

            long productPrice = daoManager
                    .createOrderProductDao()
                    .getProductPrice(orderProduct);

            long orderProductQuantity = orderProduct.getQuantity();
            orderProduct.setProductSum(orderProductQuantity * productPrice);

            daoManager
                    .createOrderProductDao()
                    .create(orderProduct);
            //setOrderTotalPrice(orderProduct, connection, orderProductDao);
        });
    }

    public void create(OrderProduct orderProduct) {

        transactionHandler.runInTransaction(connection -> {

            long productPrice = daoManager
                    .createOrderProductDao()
                    .getProductPrice(orderProduct);

            long orderProductQuantity = orderProduct.getQuantity();
            orderProduct.setProductSum(orderProductQuantity * productPrice);

            daoManager
                    .createOrderProductDao()
                    .create(orderProduct);

            Optional<Order> optionalOrder = daoManager
                    .createOrderProductDao()
                    .findOrderByOrderProductId(orderProduct.getId());

            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                daoManager.createOrderProductDao().getOrderTotalPrice(order);
                daoManager.createOrderDao().update(order);
            }
        });
    }

    public void increaseQuantityWhenAddProduct(OrderProduct orderProduct, int quantity) {

        transactionHandler.runInTransaction(connection -> {
            orderProduct.setQuantity(orderProduct.getQuantity() + quantity);
            long productPrice = daoManager.createOrderProductDao().getProductPrice(orderProduct);
            orderProduct.setProductSum((long) orderProduct.getQuantity() * productPrice);
            daoManager.createOrderProductDao().update(orderProduct);
            Optional<Order> optionalOrder = daoManager.createOrderProductDao().findOrderByOrderProductId(orderProduct.getId());
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                daoManager.createOrderProductDao().getOrderTotalPrice(order);
                daoManager.createOrderDao().update(order);
            }
        });
    }

    public void update(OrderProduct orderProduct) {

        transactionHandler.runInTransaction(connection -> {
            long quantity = orderProduct.getQuantity();
            long productPrice = daoManager.createOrderProductDao().getProductPrice(orderProduct);
            orderProduct.setProductSum(quantity * productPrice);
            daoManager.createOrderProductDao().update(orderProduct);
        });
    }

    public void delete(int id) {

        transactionHandler.runInTransaction(connection -> {
            daoManager.createOrderProductDao().delete(id);
        });
    }

    public Optional<OrderProduct> findById(int id) {
        return transactionHandler.runWithReturnStatement(connecton -> {
            daoManager.createOrderProductDao().findOne(id);
        });
    }

    public Optional<OrderProduct> getOrderProductByOrderIdAndProductId(int orderId, int productId) {

        return transactionHandler.runWithReturnStatement(connection -> {
            daoManager.createOrderProductDao().findOrderProductByOrderIdAndProductId(orderId, productId);
        });
    }

    public void deleteProductFromOrder(int orderId, int productId) {

        transactionHandler.runInTransaction(connection -> {

            Optional<OrderProduct> optionalOrderProduct = daoManager
                    .createOrderProductDao()
                    .findOrderProductByOrderIdAndProductId(orderId, productId);

            OrderProduct orderProduct = null;
            if (optionalOrderProduct.isPresent()) {
                orderProduct = optionalOrderProduct.get();
            }

            int orderProductId = orderProduct.getId();
            Optional<Order> optionalOrder = daoManager
                    .createOrderProductDao()
                    .findOrderByOrderProductId(orderProductId);

            daoManager.createOrderProductDao().deleteProductFromOrder(orderId, productId);

            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                long orderTotalPrice = daoManager.createOrderProductDao().getOrderTotalPrice(order);
                order.setTotalPrice(orderTotalPrice);
                daoManager.createOrderDao().update(order);
            }
        });
    }


    public Map<Order, Map<OrderProduct, Product>> getOrdersMap(List<Order> orders) {

        Map<Order, Map<OrderProduct, Product>> result = new HashMap<>();

        transactionHandler.runWithListReturning(connection -> {

            for (Order order : orders) {

                List<OrderProduct> orderProductList = daoManager
                        .createOrderProductDao()
                        .findOrderProductsByOrderId(order.getId());

                Map<OrderProduct, Product> orderProductMap = new HashMap<>();
                for (OrderProduct orderProduct : orderProductList) {

                    Optional<Product> optionalProduct = daoManager.createOrderProductDao()
                            .findProductByOrderProductId(orderProduct.getId());

                    if (optionalProduct.isPresent()) {
                        Product product = optionalProduct.get();
                        orderProductMap.put(orderProduct, product);
                    }

                }
                result.put(order, orderProductMap);
            }
        });
        return result;
    }
}

