package controller.commands.userOrder;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import controller.commands.validators.userOrder.UpdateUserOrderCommandValidator;
import model.entities.UserOrder;
import model.extras.Localization;
import model.services.UserOrderService;
import model.services.service.UserOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.UPDATE_USER_ORDERS_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.REDIRECTED;
import static model.constants.UrlHolder.USER_ORDER_DESTINATION_PAGE;

/**
 * This class represents updating UserOrder command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UpdateUserOrderCommand extends AbstractCommand implements Command {

    private UserOrderService userOrderService = UserOrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new UpdateUserOrderCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        UserOrder userOrder = new UserOrder.Builder()
                .setId(Integer.parseInt(USER_ORDER_ID_ATTRIBUTE))
                .setUserId(Integer.parseInt(USER_ID_ATTRIBUTE))
                .setOrderId(Integer.parseInt(ORDER_ID_ATTRIBUTE))
                .build();
        userOrderService.update(userOrder);
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, UPDATE_USER_ORDERS_SUCCESSFUL_MSG));
        request.setAttribute(USER_ORDERS_LIST_ATTRIBUTE, userOrderService.getAll());
        return USER_ORDER_DESTINATION_PAGE;
    }

}
