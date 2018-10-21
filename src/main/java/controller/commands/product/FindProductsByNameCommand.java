package controller.commands.product;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.Product;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static model.constants.AttributesHolder.PRODUCTS_LIST_ATTRIBUTE;
import static model.constants.AttributesHolder.PRODUCT_NAME_ATTRIBUTE;

/**
 * This class represents find Product by name command.
 *
 * @author dyvakyurii@gmail.com
 */
public class FindProductsByNameCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        List<Product> products = productService.getProductsByName(request.getParameter(PRODUCT_NAME_ATTRIBUTE));

        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, products);
        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

        return RespondFactory.builder()
                .request(request)
                .page("product")
                .build()
                .createPageFactory();
    }
}