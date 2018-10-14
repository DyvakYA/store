package controller.commands.product;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import model.entities.Product;
import model.extras.Localization;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.CREATE_PRODUCT_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.PRODUCT_JSP;

/**
 * This class represents creating Product command.
 *
 * @author dyvakyurii@gmail.com
 */
public class CreateProductCommand extends AbstractCommand implements Command {

    private ProductService productService=ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Product product = new Product.Builder()
                .setName(request.getParameter(PRODUCT_NAME_ATTRIBUTE))
                .setDescription(request.getParameter(PRODUCT_DESCRIPTION_ATTRIBUTE))
                .setDoublePrice(Double.parseDouble(request.getParameter(PRODUCT_PRICE_ATTRIBUTE)))
                .build();
        productService.create(product);
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, CREATE_PRODUCT_SUCCESSFUL_MSG));
        return roleCheckerSetAttributes(PRODUCT_JSP, request);
    }
}
