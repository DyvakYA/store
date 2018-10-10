package controller.commands.orderProduct;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.product.DeleteProductCommandValidator;
import model.extras.Localization;
import model.services.OrderProductService;
import model.services.service.OrderProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_PRODUCT_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.ORDER_JSP;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents deleting Product from Order command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteProductFromOrderCommand extends AbstractCommand implements Command {

    private OrderProductService orderProductService=OrderProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new DeleteProductCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        orderProductService.deleteProductFromOrder(
                Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE)),
                Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)));
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, DELETE_PRODUCT_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(ORDER_JSP, request);
    }
}
