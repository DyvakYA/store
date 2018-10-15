package model.entities;

public class UserOrderBuilder {

    private int id;
    private int userId;
    private int orderId;

    public UserOrderBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public UserOrderBuilder setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public UserOrderBuilder setOrderId(int orderId) {
        this.orderId = orderId;
        return this;
    }

    public UserOrder build() {
        return new UserOrder(id, userId, orderId);
    }
}

