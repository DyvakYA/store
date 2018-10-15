package model.entities;

import java.text.NumberFormat;

/**
 * This class represents OrderProduct entity.
 *
 * @author dyvakyurii@gmail.com
 */
public class OrderProduct implements Identified {

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private long productSum;

    public OrderProduct(int id, int orderId, int productId, int quantity, long productSum) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.productSum = productSum;
    }

    public static OrderProductBuilder builder() {
        return new OrderProductBuilder();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductSum() {
        return productSum;
    }

    public String getRealProductSum() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        return nf.format(productSum / 100);
    }

    public void setProductSum(long productSum) {
        this.productSum = productSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderProduct)) return false;

        OrderProduct that = (OrderProduct) o;

        if (id != that.id) return false;
        if (orderId != that.orderId) return false;
        if (productId != that.productId) return false;
        if (quantity != that.quantity) return false;
        return productSum == that.productSum;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderId;
        result = 31 * result + productId;
        result = 31 * result + quantity;
        result = 31 * result + (int) (productSum ^ (productSum >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", productSum=" + productSum +
                '}';
    }
}
