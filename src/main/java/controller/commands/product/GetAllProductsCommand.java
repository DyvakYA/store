package controller.commands.product;

import controller.commands.Command;
import controller.commands.AbstractCommand;
import controller.commands.pageconstructor.RespondFactory;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.PRODUCTS_LIST_ATTRIBUTE;
import static model.constants.UrlHolder.PRODUCT_JSP;

/**
 * This class represents get all Products from base.
 *
 * @author dyvakyurii@gmail.com
 */
public class GetAllProductsCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

        return RespondFactory.builder()
                .request(request)
                .page("product")
                .build()
                .createPageFactory();
    }
}
