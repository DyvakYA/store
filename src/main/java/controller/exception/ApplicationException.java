package controller.exception;

import org.apache.log4j.Logger;

/**
 * Created by Dyvak on 26.01.2017.
 */
public class ApplicationException extends RuntimeException {

    private static final Logger logger=Logger.getLogger(ApplicationException.class);

    public ApplicationException(String message) {
        super(message);
        logger.info(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
        logger.info(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        logger.info(message, cause);
    }
}
