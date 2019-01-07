package model.dao.jdbc;


import model.dao.ProductDao;
import model.entities.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


/**
 * Created by User on 5/25/2018.
 */
public class JdbcProductDaoImpl extends AbstractDao<Product> implements ProductDao {

    private static final Logger log = Logger.getLogger(JdbcProductDaoImpl.class);

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
    public Optional<Product> findOne(long id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        log.info("Get all products (Dao)");
        return super.findAll(Product.class);
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
