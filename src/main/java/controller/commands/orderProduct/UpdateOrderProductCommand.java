package controller.commands.orderProduct;

import controller.commands.Command;
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

/**
 * This class represents updating OrderProduct command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UpdateOrderProductCommand implements Command {

    private OrderProductService orderProductsService = OrderProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        OrderProduct orderProduct = OrderProduct.builder()
                .setId(Integer.parseInt(request.getParameter(ORDER_PRODUCT_ID_ATTRIBUTE)))
                .setOrderId(Integer.parseInt((request.getParameter(ORDER_ID_ATTRIBUTE))))
                .setProductId(Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)))
                .build();
        orderProductsService.update(orderProduct);
        String message = Localization.getInstance().getLocalizedMessage(request, UPDATE_ORDER_PRODUCTS_SUCCESSFUL_MSG);

        request.setAttribute(RESULT_ATTRIBUTE, message);
        request.setAttribute(ORDER_PRODUCTS_LIST_ATTRIBUTE, orderProductsService.getAll());

        return ORDER_PRODUCT_DESTINATION_PAGE;
    }

}
