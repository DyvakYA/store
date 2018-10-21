package model.services.service;

import model.dao.daofactory.DaoFactory;
import model.dao.daofactory.DaoManager;
import model.dao.daofactory.JdbcDaoManager;
import model.entities.Product;
import model.services.ProductService;
import model.services.transactions.TransactionHandler;
import model.services.transactions.TransactionHandlerImpl;

import java.util.List;

/**
 * Created by Dyvak on 21.01.2017.
 */
public class ProductServiceImpl implements ProductService {

    private TransactionHandler transactionHandler = TransactionHandlerImpl.getInstance();

    private DaoManager daoManager = new JdbcDaoManager();

    private static class Holder {
        static final ProductServiceImpl INSTANCE = new ProductServiceImpl();
    }

    public static ProductServiceImpl getInstance() {
        return Holder.INSTANCE;
    }

    public List<Product> getAll() {

        return transactionHandler.runWithOutCommit(connection -> {
            daoManager
                    .createProductDao()
                    .findAll();
        });
    }

    public void create(Product product) {
        transactionHandler.runInTransaction(connection -> {
            daoManager
                    .createProductDao()
                    .create(product);
        });
    }

    public void update(Product product) {
        transactionHandler.runInTransaction(connection -> {
            daoManager
                    .createProductDao()
                    .update(product);
        });
    }

    public void delete(int id) {
        transactionHandler.runWithOutCommit(connection -> {
            daoManager
                    .createProductDao()
                    .delete(id);
        });
    }

    public List<Product> getProductsByPrice(double doubleFirst, double doubleSecond) {

        long first = (long) doubleFirst * 100;
        long second = (long) doubleSecond * 100;

        return transactionHandler.runWithOutCommit(connection -> {
            daoManager
                    .createProductDao()
                    .findProductByPrice(first, second);
        });
    }

    public List<Product> getProductsByName(String name) {

        return transactionHandler.runWithOutCommit(connection -> {
            daoManager
                    .createProductDao()
                    .findProductsByName(name);
        });
    }
}
