package model.entities;

public class OrderProductBuilder {

    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private long productSum;

    public OrderProductBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public OrderProductBuilder setOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderProductBuilder setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public OrderProductBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderProductBuilder setProductSum(long productSum) {
        this.productSum = productSum;
        return this;
    }

    public OrderProduct build() {
        return new OrderProduct(id, orderId, productId, quantity, productSum);
    }
}

