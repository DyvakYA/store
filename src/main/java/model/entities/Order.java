package model.entities;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static model.constants.Config.DD_MM_YYYY_AT_HH_MM_SS;

/**
 * This class represents Order entity.
 *
 * @author dyvakyurii@gmail.com
 */
public class Order implements Identified {

    private int id;
    private String status;
    private Date date;
    private long totalPrice;

    private Order(int id, String status, Date date, long totalPrice) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return status;
    }

    public void setOrderStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    /**
     * @return Real amount of date in human friendly format.
     */
    public String getRealDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY_AT_HH_MM_SS);
        return dateFormat.format(new Date(date.getTime()));
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    /**
     * @return Real amount of price in human friendly format with fractional digits
     */
    public String getRealTotalPrice() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        return nf.format(totalPrice / 100);
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (totalPrice != order.totalPrice) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        return date != null ? date.equals(order.date) : order.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) (totalPrice ^ (totalPrice >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatus='" + status + '\'' +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
