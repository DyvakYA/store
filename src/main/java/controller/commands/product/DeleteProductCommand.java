package controller.commands.product;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.extras.Localization;
import model.services.ProductService;
import model.services.service.ProductServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_PRODUCT_SUCCESSFUL_MSG;

/**
 * This class represents deleting Product command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteProductCommand implements Command {

    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        productService.delete(Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)));

        request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

        String message = Localization.getLocalizedMessage(request, DELETE_PRODUCT_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return RespondFactory.builder()
                .request(request)
                .page("product")
                .build()
                .createPageFactory();
    }
}
