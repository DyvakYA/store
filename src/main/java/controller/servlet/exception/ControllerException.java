package controller.servlet.exception;

import controller.exception.ApplicationException;
import org.apache.log4j.Logger;

/**
 * Created by Dyvak on 26.01.2017.
 */
public class ControllerException extends ApplicationException {

    private static final Logger logger=Logger.getLogger(ControllerException.class);

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
