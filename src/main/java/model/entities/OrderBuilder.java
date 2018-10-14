package model.entities;

import java.util.Date;

public class OrderBuilder {

    private int id;
    private String status;
    private Date date;
    private long totalPrice;

    public OrderBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setOrderStatus(String status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public OrderBuilder setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Order build() {
        return new Order(id, status, date, totalPrice);
    }
}

