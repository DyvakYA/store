package controller.commands.orderProduct;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import controller.commands.validators.orderProduct.UpdateOrderProductCommandValidator;
import model.entities.OrderProduct;
import model.extras.Localization;
import model.services.OrderProductService;
import model.services.service.OrderProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.UPDATE_ORDER_PRODUCTS_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_PRODUCT_DESTINATION_PAGE;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents updating OrderProduct command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UpdateOrderProductCommand extends AbstractCommand implements Command {

    private OrderProductService orderProductsService=OrderProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new UpdateOrderProductCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        OrderProduct orderProduct= new OrderProduct.Builder()
                .setId(Integer.parseInt(request.getParameter(ORDER_PRODUCT_ID_ATTRIBUTE)))
                .setOrderId(Integer.parseInt((request.getParameter(ORDER_ID_ATTRIBUTE))))
                .setProductId(Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)))
                .build();
        orderProductsService.update(orderProduct);
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, UPDATE_ORDER_PRODUCTS_SUCCESSFUL_MSG));
        request.setAttribute(ORDER_PRODUCTS_LIST_ATTRIBUTE, orderProductsService.getAll());
        return ORDER_PRODUCT_DESTINATION_PAGE;
    }

}
