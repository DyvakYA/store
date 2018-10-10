package model.dao;

import model.entities.Product;

import java.util.List;


public interface ProductDao extends GenericDao<Product> {

    /**
     * Find Products by price parameters .
     * @return  list of Products with correct parameters of from and before price .
     *
     * @param first price start value.
     * @param second price finish value.
     */
    List<Product> findProductsByPrice(long first, long second);

    /**
     * Find Products by name parameter .
     * @return  list of Products with correct parameter of name product .
     *
     * @param name name value.
     */
    List<Product> findProductsByName(String name);
}
