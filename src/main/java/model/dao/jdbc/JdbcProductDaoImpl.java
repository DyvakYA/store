package model.dao.jdbc;



import model.dao.GenericDao;
import model.dao.ProductDao;
import model.entities.Product;

import java.sql.Connection;
import java.util.List;


/**
 * Created by User on 5/25/2018.
 */
public class JdbcProductDaoImpl extends AbstractDao<Product> implements ProductDao {

    private static String TABLE = "products";

    public JdbcProductDaoImpl(Connection connection) {
        super(connection, TABLE);
    }

    @Override
    public void create(Product product) {
        super.create(Product.class, product);
    }

    @Override
    public void update(Product product) {
        super.update(Product.class, product, product.getId());
    }

    @Override
    public void delete(long id) {
        super.delete(id);
    }

    @Override
    public Product findOne(long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<Product> findProductsByPrice(long first, long second) {
        return null;
    }

    @Override
    public List<Product> findProductsByName(String name) {
        return null;
    }
}
