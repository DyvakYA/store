package controller.commands.user;

import controller.commands.Command;
import controller.commands.AbstractCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.UrlHolder.USER_JSP;

/**
 * This class represents get all Users command.
 *
 * @author dyvakyurii@gmail.com
 */
public class AdminGetAllUsersCommand extends AbstractCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        return roleCheckerSetAttributes(USER_JSP, request);
    }
}
