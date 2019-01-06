package model.services.transactions;

/**
 * Created by User on 5/27/2018.
 */
public interface TransactionHandler<T> {

    void runInTransaction(Transaction transaction);

    void runWithOutCommit(Transaction transaction);

}
