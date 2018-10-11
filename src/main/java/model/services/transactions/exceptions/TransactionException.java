package model.services.transactions.exceptions;

/**
 * Created by User on 5/28/2018.
 */
public class TransactionException extends RuntimeException {

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}

