package model.services.transactions;

import model.dao.connection.DaoConnection;

@FunctionalInterface
public interface Transaction {

    void execute(DaoConnection connection);

}