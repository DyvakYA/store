package model.services.service;

import model.dao.DaoConnection;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;
import model.services.ProductService;

import java.util.List;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class ProductServiceImpl implements ProductService {

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private static class Holder {
        static final ProductServiceImpl INSTANCE = new ProductServiceImpl();
    }

    public static ProductServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<Product> getAll() {
        try (DaoConnection connection=daoFactory.getConnection()) {
            connection.beginTransaction();
            ProductDao productDao=daoFactory.createProductDao(connection);
            return productDao.findAll();
        }
    }

    public void create(Product product) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            ProductDao productDao=daoFactory.createProductDao(connection);
            productDao.create(product);
            connection.commitTransaction();
        }
    }

    public void update(Product product) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            ProductDao productDao=daoFactory.createProductDao(connection);
            productDao.update(product, product.getId());
            connection.commitTransaction();
        }
    }

    public void delete(int id) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            ProductDao productDao=daoFactory.createProductDao(connection);
            productDao.delete(id);
            connection.commitTransaction();
        }
    }

    public List<Product> getProductsByPrice(double doubleFirst, double doubleSecond) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            ProductDao productDao=daoFactory.createProductDao(connection);
            long first = (long)doubleFirst * 100;
            long second = (long)doubleSecond * 100;
            return productDao.findProductsByPrice(first, second);
        }
    }

    public List<Product> getProductsByName(String name) {
        try(DaoConnection connection = daoFactory.getConnection()) {
            connection.beginTransaction();
            ProductDao productDao=daoFactory.createProductDao(connection);
            return productDao.findProductsByName(name);
        }
    }
}
