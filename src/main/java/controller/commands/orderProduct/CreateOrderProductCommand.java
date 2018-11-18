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
import static model.constants.MsgHolder.CREATE_ORDER_PRODUCTS_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_PRODUCT_DESTINATION_PAGE;

/**
 * This class represents creating OrderProduct command.
 *
 * @author dyvakyurii@gmail.com
 */
public class CreateOrderProductCommand implements Command {

    private OrderProductService orderProductService = OrderProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        OrderProduct orderProduct = OrderProduct.builder()
                .setOrderId(Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE)))
                .setProductId(Integer.valueOf(request.getParameter(PRODUCT_ID_ATTRIBUTE)))
                .build();
        orderProductService.create(orderProduct);

        request.setAttribute(ORDER_PRODUCTS_LIST_ATTRIBUTE, orderProductService.getAll());

        String message = Localization.getLocalizedMessage(request, CREATE_ORDER_PRODUCTS_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return ORDER_PRODUCT_DESTINATION_PAGE;
    }
}
