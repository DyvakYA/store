package controller.commands.order;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.order.UpdateOrderStatusCommandValidator;
import model.entities.Order;
import model.extras.Localization;
import model.services.OrderService;
import model.services.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.UPDATE_ORDER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_JSP;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents admin update status of order.
 *
 * @author dyvakyurii@gmail.com
 */
public class AdminUpdateOrderStatusCommand extends AbstractCommand implements Command {

    private OrderService orderService=OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new UpdateOrderStatusCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        Order order= new Order.Builder()
                .setId(Integer.valueOf(request.getParameter(ORDER_ID_ATTRIBUTE)))
                .setOrderStatus(request.getParameter(ORDER_STATUS_ATTRIBUTE))
                .build();
        orderService.updateOrderStatus(order);
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, UPDATE_ORDER_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(ORDER_JSP, request);
    }
}
