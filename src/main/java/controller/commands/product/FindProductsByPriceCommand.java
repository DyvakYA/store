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

import static model.constants.AttributesHolder.*;

/**
 * This class represents find Product by price command.
 *
 * @author dyvakyurii@gmail.com
 */
public class FindProductsByPriceCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Double from = Double.parseDouble(request.getParameter(PRICE_FIRST_ATTRIBUTE));
        Double to = Double.parseDouble(request.getParameter(PRICE_SECOND_ATTRIBUTE));

        List<Product> products = productService.getProductsByPrice(from, to);

        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, products);
        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

        return RespondFactory.builder()
                .request(request)
                .page("product")
                .build()
                .createPageFactory();
    }
}

