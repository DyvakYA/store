package controller.commands.product;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.Product;
import model.extras.Localization;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.CREATE_PRODUCT_SUCCESSFUL_MSG;

/**
 * This class represents creating Product command.
 *
 * @author dyvakyurii@gmail.com
 */
public class CreateProductCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Product product = Product.builder()
                .setName(request.getParameter(PRODUCT_NAME_ATTRIBUTE))
                .setDescription(request.getParameter(PRODUCT_DESCRIPTION_ATTRIBUTE))
                .setDoublePrice(Double.parseDouble(request.getParameter(PRODUCT_PRICE_ATTRIBUTE)))
                .build();
        productService.create(product);

        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

        String message = Localization.getInstance().getLocalizedMessage(request, CREATE_PRODUCT_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return RespondFactory.builder()
                .request(request)
                .page("product")
                .build()
                .createPageFactory();
    }
}
