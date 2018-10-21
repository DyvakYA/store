package controller.commands.userOrder;

import controller.commands.Command;
import model.extras.Localization;
import model.services.UserOrderService;
import model.services.service.UserOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_USER_ORDERS_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.USER_ORDER_DESTINATION_PAGE;

/**
 * This class represents deleting UserOrder command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteUserOrderCommand implements Command {

    private UserOrderService userOrderService = UserOrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        userOrderService.delete(Integer.valueOf(request.getParameter(USER_ID_ATTRIBUTE)));

        request.setAttribute(USER_ORDERS_LIST_ATTRIBUTE, userOrderService.getAll());

        String message = Localization.getInstance().getLocalizedMessage(request, DELETE_USER_ORDERS_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return USER_ORDER_DESTINATION_PAGE;
    }
}
