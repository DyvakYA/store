package model.services.service;

import model.dao.ProductDao;
import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.JdbcDaoFactory;
import model.entities.Product;
import model.services.ProductService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class ProductServiceImpl implements ProductService {

    private static final Logger log = Logger.getLogger(ProductServiceImpl.class);

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();
    private DaoFactory daoFactory = JdbcDaoFactory.getInstance();

    private ProductServiceImpl() {

    }

    private static class Holder {
        static final ProductServiceImpl INSTANCE = new ProductServiceImpl();
    }

    public static ProductServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<Product> getAll() {

        log.info("Get all products (Service)");

        AtomicReference<List<Product>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            ProductDao dao = daoFactory.createProductDao();
            log.info(dao.findAll());
            result.set(dao.findAll());
        });
        return result.get();
    }

    public void create(Product product) {

        log.info("create new product (Service)");

        transactionHandler.runInTransaction(connection -> {
            daoFactory
                    .createProductDao()
                    .create(product);
        });
    }

    public void update(Product product) {
        transactionHandler.runInTransaction(connection -> {
            daoFactory
                    .createProductDao()
                    .update(product);
        });
    }

    public void delete(int id) {
        transactionHandler.runWithOutCommit(connection -> {
            daoFactory
                    .createProductDao()
                    .delete(id);
        });
    }

    public List<Product> getProductsByPrice(double doubleFirst, double doubleSecond) {

        long first = (long) doubleFirst * 100;
        long second = (long) doubleSecond * 100;

        AtomicReference<List<Product>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory
                    .createProductDao()
                    .findProductsByPrice(first, second));
        });
        return result.get();
    }

    public List<Product> getProductsByName(String name) {

        AtomicReference<List<Product>> result = new AtomicReference<>(Collections.emptyList());
        transactionHandler.runWithOutCommit(connection -> {
            result.set(daoFactory
                    .createProductDao()
                    .findProductsByName(name));
        });
        return result.get();
    }

}
