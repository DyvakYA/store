package model.services;

import model.entities.Product;

import java.util.List;

/**
 * This class represents Product service
 *
 * @author dyvakyurii@gmail.com
 */
public interface ProductService {

    /**
     * @return list of Products from base.
     */
    List<Product> getAll();

    /**
     * Create Product.
     */
    void create(Product product);

    /**
     * update Product in the base.
     */
    void update(Product product);

    /**
     * @param id id of the Product.
     * delete Product from base.
     */
    void delete(int id);

    /**
     * @param first find Products from price.
     * @param second find Products before price.
     * @return list of Products with specified price values
     * from base.
     */
    List<Product> getProductsByPrice(double first, double second);

    /**
     * @param name find Products wich contains name value.
     * @return list of Products with specified name values
     * from base.
     */
    List<Product> getProductsByName(String name);

}
