package controller.commands.order;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.order.DeleteOrderCommandValidator;
import model.extras.Localization;
import model.services.OrderService;
import model.services.service.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.ORDER_ID_ATTRIBUTE;
import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.MsgHolder.DELETE_ORDER_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_JSP;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents deleting order command .
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteOrderCommand extends AbstractCommand implements Command {

    private OrderService orderService=OrderServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new DeleteOrderCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        orderService.delete(Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE)));
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, DELETE_ORDER_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(ORDER_JSP, request);
    }
}
