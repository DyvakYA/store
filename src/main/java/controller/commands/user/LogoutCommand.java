package controller.commands.user;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import model.entities.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static model.constants.AttributesHolder.USER_SESSION_ATTRIBUTE;
import static model.constants.UrlHolder.INDEX;

/**
 * This class represents logout for User command.
 *
 * @author dyvakyurii@gmail.com
 */
public class LogoutCommand extends AbstractCommand implements Command {

    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    private static final String USER_LOGGED_OUT = "%s id=%s LOGGED OUT.";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(USER_SESSION_ATTRIBUTE);
        logger.info(String.format(USER_LOGGED_OUT, user.getEmail(), user.getId()));
        request.getSession().invalidate();
        return INDEX;
    }
}

