package model.dao.jdbc;

import model.dao.OrderProductDao;
import model.dao.exception.DAOException;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.constants.AttributesHolder.PRODUCT_PRICE_ATTRIBUTE;
import static model.constants.ErrorMsgHolder.SQL_EXCEPTION;

/**
 * This class is the implementation of OrderProduct entity DAO
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcOrderProductDao extends AbstractDao<OrderProduct> implements OrderProductDao {

    private static final String SELECT_FROM_ORDER_PRODUCT_WHERE_ORDER_PRODUCT_ID="SELECT * FROM order_products " +
            "WHERE order_product_id=?";
    private static final String SELECT_FROM_ORDER_PRODUCT="SELECT * FROM order_products";
    private static final String CREATE_ORDER_PRODUCT_QUERY="INSERT INTO order_products (order_id, product_id, quantity, product_sum)  " +
            "VALUES (?, ?, ?, ?)";
    private static final String SELECT_FROM_ORDER_PRODUCT_WHERE_ORDER_ID_AND_PRODUCT_ID="SELECT * FROM order_products WHERE order_id=? AND product_id=?";
    private static final String UPDATE_ORDER_PRODUCT_QUERY="UPDATE order_products SET order_id=?, product_id=?, quantity=?, product_sum=? " +
            "WHERE order_product_id=?";
    private static final String DELETE_ORDER_PRODUCT_QUERY="DELETE FROM order_products WHERE order_id=?";
    private static final String DELETE_PRODUCT_FROM_ORDER_QUERY="DELETE FROM order_products WHERE product_id=? AND order_id=?";
    private static final String SELECT_ORDER_PRODUCTS_BY_ORDER_ID="SELECT * FROM order_products " +
            "WHERE order_id=?";
    private static final String SELECT_PRODUCTS_BY_ORDER_PRODUCT_ID="SELECT order_products.order_id," +
            "products.product_id, products.product_name,products.product_description, products.product_price,order_products.quantity " +
            "FROM order_products INNER JOIN products " +
            "ON order_products.product_id=products.product_id " +
            "WHERE order_products.order_product_id=?";
    private static final String SELECT_PRODUCT_PRICE_FROM_ORDER_PRODUCT="SELECT product_price\n" +
            "FROM products \n" +
            "WHERE product_id=?";
    private static final String SELECT_ORDER_TOTAL_PRICE="SELECT Sum(product_sum) \n" +
            "FROM order_products\n" +
            "WHERE order_id = ?;";
    private static final String SELECT_ORDER_BY_ORDER_PRODUCT_ID="SELECT orders.order_id, " +
            "orders.order_status, orders.order_date, orders.order_sum \n" +
            "FROM orders INNER JOIN order_products\n" +
            "ON orders.order_id=order_products.order_id WHERE order_products.order_product_id=?";


    JdbcOrderProductDao(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<OrderProduct> findById(int id) {
        Optional<OrderProduct> orderProduct=Optional.empty();
        try (PreparedStatement query=connection.prepareStatement(SELECT_FROM_ORDER_PRODUCT_WHERE_ORDER_PRODUCT_ID)) {
            query.setInt(1, id);
            ResultSet resultSet=query.executeQuery();
            if (resultSet.next()) {
                orderProduct=Optional.of(resultSetExtractor.getOrderProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return orderProduct;
    }

    @Override
    public List<OrderProduct> findAll() {
        List<OrderProduct> orderProducts=new ArrayList<>();
        try (PreparedStatement query=connection.prepareStatement(SELECT_FROM_ORDER_PRODUCT)) {
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                orderProducts.add(resultSetExtractor.getOrderProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return orderProducts;
    }

    @Override
    public void deleteProductFromOrder(int orderId, int productId) {
        try (PreparedStatement query=connection.prepareStatement(DELETE_PRODUCT_FROM_ORDER_QUERY)) {
            query.setInt(1, productId);
            query.setInt(2, orderId);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public Optional<OrderProduct> findOrderProductByOrderIdAndProductId(int orderId, int productId) {
        Optional<OrderProduct> orderProduct = Optional.empty();
        try (PreparedStatement query=connection
                .prepareStatement(SELECT_FROM_ORDER_PRODUCT_WHERE_ORDER_ID_AND_PRODUCT_ID)) {
            query.setInt(1, orderId);
            query.setInt(2, productId);
            ResultSet resultSet=query.executeQuery();
            if (resultSet.next()) {
                orderProduct=Optional.ofNullable(resultSetExtractor.getOrderProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return orderProduct;
    }

    @Override
    public List<OrderProduct> findOrderProductsByOrderId(int orderId) {
        List<OrderProduct> orderProducts=new ArrayList<>();
        try (PreparedStatement query=connection.prepareStatement(SELECT_ORDER_PRODUCTS_BY_ORDER_ID)) {
            query.setInt(1, orderId);
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                orderProducts.add(resultSetExtractor.getOrderProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return orderProducts;
    }

    @Override
    public Optional<Product> findProductByOrderProductId(int id) {
        return getProduct(id, SELECT_PRODUCTS_BY_ORDER_PRODUCT_ID);
    }

    @Override
    public long getProductPrice(OrderProduct orderProduct) {
        int price=0;
        try (PreparedStatement query=connection
                .prepareStatement(SELECT_PRODUCT_PRICE_FROM_ORDER_PRODUCT)) {
            query.setInt(1, orderProduct.getProductId());
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                price=resultSet.getInt(PRODUCT_PRICE_ATTRIBUTE);
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return price;
    }

    @Override
    public long getOrderTotalPrice(Order order) {
        long price=0;
        try (PreparedStatement query=connection
                .prepareStatement(SELECT_ORDER_TOTAL_PRICE)) {
            query.setInt(1, order.getId());
            ResultSet resultSet=query.executeQuery();
            while (resultSet.next()) {
                price=resultSet.getLong("Sum(product_sum)");
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return price;
    }

    @Override
    public Optional<Order> findOrderByOrderProductId(int id) {
        return getOrder(id, SELECT_ORDER_BY_ORDER_PRODUCT_ID);
    }

    @Override
    public void create(OrderProduct orderProduct) {
        checkForNull(orderProduct);
        checkIsUnsaved(orderProduct);
        try (PreparedStatement query=connection.prepareStatement(CREATE_ORDER_PRODUCT_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            query.setInt(1, orderProduct.getOrderId());
            query.setInt(2, orderProduct.getProductId());
            query.setInt(3, orderProduct.getQuantity());
            query.setLong(4, orderProduct.getProductSum());
            query.executeUpdate();
            ResultSet resultSet=query.getGeneratedKeys();
            if (resultSet.next()) {
                orderProduct.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public void update(OrderProduct orderProduct, int id) {
        checkForNull(orderProduct);
        checkIsSaved(orderProduct);
        try (PreparedStatement query=connection.prepareStatement(UPDATE_ORDER_PRODUCT_QUERY)) {
            query.setInt(1, orderProduct.getOrderId());
            query.setInt(2, orderProduct.getProductId());
            query.setInt(3, orderProduct.getQuantity());
            query.setLong(4, orderProduct.getProductSum());
            query.setInt(5, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement query=connection.prepareStatement(DELETE_ORDER_PRODUCT_QUERY)) {
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }
}
