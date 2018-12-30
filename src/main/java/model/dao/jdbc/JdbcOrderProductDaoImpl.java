package model.dao.jdbc;

import model.dao.OrderProductDao;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcOrderProductDaoImpl extends AbstractDao<OrderProduct> implements OrderProductDao {

    private static String TABLE = "order_product";

    public JdbcOrderProductDaoImpl(Connection connection) {
        super(connection, TABLE);
    }

    @Override
    public void create(OrderProduct orderProduct) {

    }

    @Override
    public void update(OrderProduct orderProduct) {

    }

    @Override
    public OrderProduct findOne(long id) {
        return null;
    }

    @Override
    public List<OrderProduct> findAll() {
        return null;
    }

    @Override
    public void deleteProductFromOrder(int orderId, int productId) {

    }

    @Override
    public Optional<OrderProduct> findOrderProductByOrderIdAndProductId(int orderId, int productId) {
        return Optional.empty();
    }

    @Override
    public List<OrderProduct> findOrderProductsByOrderId(int orderId) {
        return null;
    }

    @Override
    public Optional<Product> findProductByOrderProductId(int orderProductId) {
        return Optional.empty();
    }

    @Override
    public long getProductPrice(OrderProduct orderProduct) {
        return 0;
    }

    @Override
    public long getOrderTotalPrice(Order order) {
        return 0;
    }

    @Override
    public Optional<Order> findOrderByOrderProductId(int id) {
        return Optional.empty();
    }
}
