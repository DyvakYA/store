package controller.commands.orderProduct;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import controller.commands.validators.orderProduct.DeleteOrderProductCommandValidator;
import model.extras.Localization;
import model.services.OrderProductService;
import model.services.service.OrderProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_ORDER_PRODUCTS_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_PRODUCT_DESTINATION_PAGE;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents deleting OrderProduct command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteOrderProductCommand extends AbstractCommand implements Command {

    private OrderProductService orderProductsService = OrderProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new DeleteOrderProductCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        orderProductsService.delete(Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE)));
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, DELETE_ORDER_PRODUCTS_SUCCESSFUL_MSG));
        request.setAttribute(ORDER_PRODUCTS_LIST_ATTRIBUTE, orderProductsService.getAll());
        return ORDER_PRODUCT_DESTINATION_PAGE;
    }

}
