package model.entities;

public class ProductBuilder {

    private int id;
    private String name;
    private String description;
    private long price;

    public ProductBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder setDoublePrice(double price) {
        this.price = (long) price * 100;
        return this;
    }

    public ProductBuilder setPrice(long price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(id, name, description, price);
    }
}
