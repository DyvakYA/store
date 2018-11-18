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
import static model.constants.MsgHolder.UPDATE_PRODUCT_SUCCESSFUL_MSG;

/**
 * This class represents updating Product command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UpdateProductCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Product product = Product.builder()
                .setId(Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)))
                .setName(request.getParameter(PRODUCT_NAME_ATTRIBUTE))
                .setDescription(request.getParameter(PRODUCT_DESCRIPTION_ATTRIBUTE))
                .setDoublePrice(Double.parseDouble(request.getParameter(PRODUCT_PRICE_ATTRIBUTE)))
                .build();
        productService.update(product);

        String message = Localization.getLocalizedMessage(request, UPDATE_PRODUCT_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return RespondFactory.builder()
                .request(request)
                .page("products")
                .build()
                .createPageFactory();
    }
}
