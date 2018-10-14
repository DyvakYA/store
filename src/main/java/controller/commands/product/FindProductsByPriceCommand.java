package controller.commands.product;

import controller.commands.AbstractCommand;
import controller.commands.Command;
import model.entities.Product;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static model.constants.AttributesHolder.*;
import static model.constants.UrlHolder.PRODUCT_JSP;

/**
 * This class represents find Product by price command.
 *
 * @author dyvakyurii@gmail.com
 */
public class FindProductsByPriceCommand extends AbstractCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<Product> products = productService.getProductsByPrice(
                Double.parseDouble(request.getParameter(PRICE_FIRST_ATTRIBUTE)),
                Double.parseDouble(request.getParameter(PRICE_SECOND_ATTRIBUTE)));
        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, products);
        return roleCheckerDestinationPageReturner(PRODUCT_JSP, request);
    }
}

