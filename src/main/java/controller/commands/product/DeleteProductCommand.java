package controller.commands.product;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import model.extras.Localization;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.PRODUCT_ID_ATTRIBUTE;
import static model.constants.AttributesHolder.RESULT_ATTRIBUTE;
import static model.constants.MsgHolder.DELETE_PRODUCT_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.PRODUCT_JSP;

/**
 * This class represents deleting Product command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteProductCommand extends AbstractCommand implements Command {

    private ProductService productService=ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        productService.delete(Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)));
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, DELETE_PRODUCT_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(PRODUCT_JSP, request);
    }
}
