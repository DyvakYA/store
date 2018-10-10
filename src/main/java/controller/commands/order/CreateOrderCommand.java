package controller.commands.order;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.order.CreateOrderCommandValidator;
import model.entities.Order;
import model.extras.Localization;
import model.services.OrderService;
import model.services.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

import static model.constants.AttributesHolder.ORDER_STATUS_ATTRIBUTE;
import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.MsgHolder.CREATE_ORDER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_JSP;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents admin create order.
 *
 * @author dyvakyurii@gmail.com
 */
public class CreateOrderCommand extends AbstractCommand implements Command {

    private OrderService orderService=OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new CreateOrderCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        Order order = new Order.Builder()
                .setOrderStatus(request.getParameter(ORDER_STATUS_ATTRIBUTE))
                .setDate(new Timestamp(System.currentTimeMillis()))
                .build();
        orderService.create(order);
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, CREATE_ORDER_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(ORDER_JSP, request);
    }
}
