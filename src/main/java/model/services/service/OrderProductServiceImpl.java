package model.services.service;

import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.UserOrder;
import model.services.OrderProductService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class OrderProductServiceImpl implements OrderProductService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();

    private DaoFactory daoFactory = JdbcDaoFactory.getInstance();

    private OrderProductServiceImpl() {

    }

    private static class Holder {
        static final OrderProductServiceImpl INSTANCE = new OrderProductServiceImpl();
    }

    public static OrderProductServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<OrderProduct> getAll() {
        AtomicReference<List<OrderProduct>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory
                    .createOrderProductDao()
                    .findAll());
        });
        return result.get();
    }

    public void createUserOrderAndOrderProduct(int userId, int orderId, int productId, int quantity) {

        transactionHandler.runInTransaction(connection -> {

            UserOrder userOrder = UserOrder.builder()
                    .setUserId(userId)
                    .setOrderId(orderId)
                    .build();

            daoFactory
                    .createUserOrderDao()
                    .create(userOrder);

            OrderProduct orderProduct = OrderProduct.builder()
                    .setOrderId(orderId)
                    .setProductId(productId)
                    .setQuantity(quantity)
                    .build();

            long productPrice = daoFactory
                    .createOrderProductDao()
                    .getProductPrice(orderProduct);

            long orderProductQuantity = orderProduct.getQuantity();
            orderProduct.setProductSum(orderProductQuantity * productPrice);

            daoFactory
                    .createOrderProductDao()
                    .create(orderProduct);
            //setOrderTotalPrice(orderProduct, connection, orderProductDao);
        });
    }

    public void create(OrderProduct orderProduct) {

        transactionHandler.runInTransaction(connection -> {

            long productPrice = daoFactory
                    .createOrderProductDao()
                    .getProductPrice(orderProduct);

            long orderProductQuantity = orderProduct.getQuantity();
            orderProduct.setProductSum(orderProductQuantity * productPrice);

            daoFactory
                    .createOrderProductDao()
                    .create(orderProduct);

            Optional<Order> optionalOrder = daoFactory
                    .createOrderProductDao()
                    .findOrderByOrderProductId(orderProduct.getId());

            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                daoFactory.createOrderProductDao().getOrderTotalPrice(order);
                daoFactory.createOrderDao().update(order);
            }
        });
    }

    public void increaseQuantityWhenAddProduct(OrderProduct orderProduct, int quantity) {

        transactionHandler.runInTransaction(connection -> {
            orderProduct.setQuantity(orderProduct.getQuantity() + quantity);
            long productPrice = daoFactory.createOrderProductDao().getProductPrice(orderProduct);
            orderProduct.setProductSum((long) orderProduct.getQuantity() * productPrice);
            daoFactory.createOrderProductDao().update(orderProduct);
            Optional<Order> optionalOrder = daoFactory.createOrderProductDao().findOrderByOrderProductId(orderProduct.getId());
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                daoFactory.createOrderProductDao().getOrderTotalPrice(order);
                daoFactory.createOrderDao().update(order);
            }
        });
    }

    public void update(OrderProduct orderProduct) {

        transactionHandler.runInTransaction(connection -> {
            long quantity = orderProduct.getQuantity();
            long productPrice = daoFactory.createOrderProductDao().getProductPrice(orderProduct);
            orderProduct.setProductSum(quantity * productPrice);
            daoFactory.createOrderProductDao().update(orderProduct);
        });
    }

    public void delete(int id) {

        transactionHandler.runInTransaction(connection -> {
            daoFactory.createOrderProductDao().delete(id);
        });
    }

    public Optional<OrderProduct> findById(int id) {

        AtomicReference<Optional<OrderProduct>> result = new AtomicReference<>(Optional.empty());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory.createOrderProductDao().findOne(id));
        });
        return result.get();
    }

    public Optional<OrderProduct> getOrderProductByOrderIdAndProductId(int orderId, int productId) {

        AtomicReference<Optional<OrderProduct>> result = new AtomicReference<>(Optional.empty());
        transactionHandler.runInTransaction(connection -> {
            result.set(daoFactory.createOrderProductDao().findOrderProductByOrderIdAndProductId(orderId, productId));
        });
        return result.get();
    }

    public void deleteProductFromOrder(int orderId, int productId) {

        transactionHandler.runInTransaction(connection -> {

            Optional<OrderProduct> optionalOrderProduct = daoFactory
                    .createOrderProductDao()
                    .findOrderProductByOrderIdAndProductId(orderId, productId);

            OrderProduct orderProduct = null;
            if (optionalOrderProduct.isPresent()) {
                orderProduct = optionalOrderProduct.get();
            }

            int orderProductId = orderProduct.getId();
            Optional<Order> optionalOrder = daoFactory
                    .createOrderProductDao()
                    .findOrderByOrderProductId(orderProductId);

            daoFactory.createOrderProductDao().deleteProductFromOrder(orderId, productId);

            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                long orderTotalPrice = daoFactory.createOrderProductDao().getOrderTotalPrice(order);
                order.setTotalPrice(orderTotalPrice);
                daoFactory.createOrderDao().update(order);
            }
        });
    }


    public Map<Order, Map<OrderProduct, Product>> getOrdersMap(List<Order> orders) {

//        Map<Order, Map<OrderProduct, Product>> result = new HashMap<>();
//
//        transactionHandler.runWithListReturning(connection -> {
//
//            for (Order order : orders) {
//
//                List<OrderProduct> orderProductList = daoFactory
//                        .createOrderProductDao()
//                        .findOrderProductsByOrderId(order.getId());
//
//                Map<OrderProduct, Product> orderProductMap = new HashMap<>();
//                for (OrderProduct orderProduct : orderProductList) {
//
//                    Optional<Product> optionalProduct = daoFactory.createOrderProductDao()
//                            .findProductByOrderProductId(orderProduct.getId());
//
//                    if (optionalProduct.isPresent()) {
//                        Product product = optionalProduct.get();
//                        orderProductMap.put(orderProduct, product);
//                    }
//
//                }
//                result.put(order, orderProductMap);
//            }
//        });
//        return result;
        return null;
    }
}

