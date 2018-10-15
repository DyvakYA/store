package model.entities;

/**
 * This class represents UserOrder entity.
 *
 * @author dyvakyurii@gmail.com
 */
public class UserOrder implements Identified {

    private int id;
    private int userId;
    private int orderId;

    public UserOrder(int id, int userId, int orderId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
    }

    public static UserOrderBuilder builder() {
        return new UserOrderBuilder();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserOrder)) return false;

        UserOrder userOrder = (UserOrder) o;

        if (id != userOrder.id) return false;
        if (userId != userOrder.userId) return false;
        return orderId == userOrder.orderId;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + orderId;
        return result;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                '}';
    }
}
