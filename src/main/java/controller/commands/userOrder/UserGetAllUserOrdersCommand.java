package controller.commands.userOrder;

import controller.commands.Command;
import model.services.UserOrderService;
import model.services.service.UserOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.USER_ORDERS_LIST_ATTRIBUTE;
import static model.constants.UrlHolder.USER_ORDER_DESTINATION_PAGE;

/**
 * This class represents get all UserOrder from base command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UserGetAllUserOrdersCommand implements Command {

    private UserOrderService userOrderService = UserOrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {


        request.setAttribute(USER_ORDERS_LIST_ATTRIBUTE, userOrderService.getAll());
        return USER_ORDER_DESTINATION_PAGE;
    }
}
