package controller.commands.product;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.validators.product.UpdateProductCommandValidator;
import model.entities.Product;
import model.extras.Localization;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.UPDATE_PRODUCT_SUCCESSFUL_MSG;
import static model.constants.UrlHolder.PRODUCT_JSP;
import static model.constants.UrlHolder.REDIRECTED;

/**
 * This class represents updating Product command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UpdateProductCommand extends AbstractCommand implements Command {

    private ProductService productService=ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        if (!new UpdateProductCommandValidator().validate(request, response)) {
            return REDIRECTED;
        }
        Product product=new Product.Builder()
                .setId(Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)))
                .setName(request.getParameter(PRODUCT_NAME_ATTRIBUTE))
                .setDescription(request.getParameter(PRODUCT_DESCRIPTION_ATTRIBUTE))
                .setDoublePrice(Double.parseDouble(request.getParameter(PRODUCT_PRICE_ATTRIBUTE)))
                .build();
        productService.update(product);
        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, UPDATE_PRODUCT_SUCCESSFUL_MSG));
        return roleCheckerDestinationPageReturner(PRODUCT_JSP, request);
    }
}
