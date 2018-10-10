package model.services;

import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents OrderProduct service
 *
 * @author dyvakyurii@gmail.com
 */
public interface OrderProductService {

    /**
     * @return list of OrderProducts from base.
     */
    List<OrderProduct> getAll();

    /**
     * Create OrderProduct.
     */
    void create(OrderProduct orderProduct);

    /**
     * update OrderProduct in the base.
     */
    void update(OrderProduct orderProduct);

    /**
     * @param id id of the OrderProduct
     * delete OrderProduct from base.
     */
    void delete(int id);

    /**
     * @param id id of the OrderProduct.
     * @return OrderProduct with specified id
     */
    Optional<OrderProduct> findById(int id);

    /**
     * @param orderId id of the order
     * @param productId id of the product
     * @return  list of OrderProducts with specified orderId and productId
     */
    Optional<OrderProduct> getOrderProductByOrderIdAndProductId(int orderId, int productId);

    /**
     * @param orderId id of the order
     * @param productId id of the product
     * Delete OrderProduct with specified orderId and productId
     */
    void deleteProductFromOrder(int orderId, int productId);

    /**
     * @param orderList is List of Orders, this Orders will be keys in Map.
     * @return Map where key is Order and value is one more
     * Map with key OrderProduct and value Product (Order->OrderProduct->Product)
     */
    Map<Order,Map<OrderProduct,Product>> getOrdersMap(List<Order> orderList);

    /**
     * @param userId,
     * @param orderId,
     * @param productId,
     * @param quantity, all it`s parameters from JSP page, need for creating UserOrder
     *                  and OrderProduct, and join with Order
     */
    void createUserOrderAndOrderProduct(int userId, int orderId, int productId, int quantity);

    /**
     * @param quantity id of the Products
     * When user add to Order, some Product which exists in Order, quantity increase.
     */
    void increaseQuantityWhenAddProduct(OrderProduct orderProduct, int quantity);
}
