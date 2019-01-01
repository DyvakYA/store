package controller.commands.product;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.PRODUCTS_LIST_ATTRIBUTE;

/**
 * This class represents get all Products from base.
 *
 * @author dyvakyurii@gmail.com
 */
public class GetAllProductsCommand implements Command {

    private static final Logger log = Logger.getLogger(GetAllProductsCommand.class);

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        log.info("Get all products command (Controller)");

        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

        log.info(request.getAttribute(PRODUCTS_LIST_ATTRIBUTE));
        log.info(productService.getAll());

        String result = RespondFactory.builder()
                .request(request)
                .page("product")
                .build()
                .createPageFactory();
        log.info(result);
        return result;
    }
}
