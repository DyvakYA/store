package model.entities;

import java.text.NumberFormat;

/**
 * This class represents Product entity.
 *
 * @author dyvakyurii@gmail.com
 */
public class Product implements Identified {

    private int id;
    private String name;
    private String description;
    private long price;

    public static class Builder {
        Product instance=new Product();

        public Builder setId(int id) {
            instance.id=id;
            return this;
        }

        public Builder setName(String name) {
            instance.name=name;
            return this;
        }

        public Builder setDescription(String description) {
            instance.description=description;
            return this;
        }

        public Builder setDoublePrice(double price) {
            instance.price=(long)price*100;
            return this;
        }

        public Builder setPrice(long price) {
            instance.price=price;
            return this;
        }

        public Product build() {
            return instance;
        }


    }

    public void setId(int id) {
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public long getPrice() {
        return price;
    }

    public String getRealPrice() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        return nf.format(price / 100);
    }

    public void setPrice(long price) {
        this.price=price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product=(Product) o;

        if (id != product.id) return false;
        if (price != product.price) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return description != null ? description.equals(product.description) : product.description == null;
    }

    @Override
    public int hashCode() {
        int result=id;
        result=31 * result + (name != null ? name.hashCode() : 0);
        result=31 * result + (description != null ? description.hashCode() : 0);
        result=31 * result + (int) (price ^ (price >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
