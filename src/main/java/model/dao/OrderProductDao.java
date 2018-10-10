package model.dao;

import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;

import java.util.List;
import java.util.Optional;

public interface OrderProductDao extends GenericDao<OrderProduct> {

    /**
     * Delete Product from order.
     *
     * @param orderId in what order need to delete product.
     * @param productId what product need to delete.
     */
    void deleteProductFromOrder(int orderId, int productId);

    /**
     * Find Order by orderId and productId in order.
     * @return  OrderProduct with correct parameters orderId and productId
     *@param orderId with this Order need to find OrderProduct.
     * @param productId with this Product need to find OrderProduct.
     */
    Optional<OrderProduct> findOrderProductByOrderIdAndProductId(int orderId, int productId);

    /**
     * Find OrderProducts by orderId .
     * @return  list of OrderProducts with correct parameter of orderId.
     *
     * @param orderId in what order need to delete product.
     */
    List<OrderProduct> findOrderProductsByOrderId(int orderId);

    /**
     * Find OrderProducts by orderId .
     * @return  Product from ProductOrder.
     *
     * @param orderProductId in what OrderProduct need to find Product.
     */
    Optional<Product> findProductByOrderProductId(int orderProductId);

    /**
     * get product prise by OrderProducts.
     * @return  long value of price.
     *
     */
    long getProductPrice(OrderProduct orderProduct);

    /**
     * get order total price by Order.
     * @return  long vale of total price.
     *
     */
    long getOrderTotalPrice(Order order);

    /**
     * get order total price by Order.
     * @return  long vale of total price.
     *
     */
    Optional<Order> findOrderByOrderProductId(int id);
}


