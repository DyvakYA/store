package controller.commands.orderProduct;

import controller.commands.Command;
import model.extras.Localization;
import model.services.OrderProductService;
import model.services.service.OrderProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_ORDER_PRODUCTS_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_PRODUCT_DESTINATION_PAGE;

/**
 * This class represents deleting OrderProduct command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteOrderProductCommand implements Command {

    private OrderProductService orderProductsService = OrderProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        orderProductsService.delete(Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE)));
        request.setAttribute(ORDER_PRODUCTS_LIST_ATTRIBUTE, orderProductsService.getAll());

        String message = Localization.getInstance().getLocalizedMessage(request, DELETE_ORDER_PRODUCTS_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return ORDER_PRODUCT_DESTINATION_PAGE;
    }

}
