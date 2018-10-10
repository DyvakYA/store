package controller.commands.order;

import controller.commands.AbstractCommand;
import controller.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.UrlHolder.ORDER_JSP;

/**
 * This class represents admin's get all orders from base.
 *
 * @author dyvakyurii@gmail.com
 */
public class AdminGetAllOrdersCommand extends AbstractCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        return roleCheckerSetAttributes(ORDER_JSP, request);
    }
}
