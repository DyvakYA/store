package model.services.transactions;

import java.util.List;
import java.util.Optional;

/**
 * Created by User on 5/27/2018.
 */
public interface TransactionHandler<T> {

    void runInTransaction(Transaction transaction);

    void runWithOutCommit(Transaction transaction);

    Optional<T> runWithReturnStatement(Transaction transaction);

    List<T> runWithListReturning(Transaction transaction);


}
