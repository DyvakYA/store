package model.dao.exception;

import controller.exception.ApplicationException;

/**
 * Created by Dyvak on 26.01.2017.
 */
public class DAOException extends ApplicationException {

    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
