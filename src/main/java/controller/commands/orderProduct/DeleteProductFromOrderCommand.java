package controller.commands.orderProduct;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.extras.Localization;
import model.services.OrderProductService;
import model.services.UserService;
import model.services.service.OrderProductServiceImpl;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.DELETE_PRODUCT_SUCCESSFUL_MSG;

/**
 * This class represents deleting Product from Order command.
 *
 * @author dyvakyurii@gmail.com
 */
public class DeleteProductFromOrderCommand implements Command {

    private OrderProductService orderProductService = OrderProductServiceImpl.getInstance();

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        orderProductService.deleteProductFromOrder(
                Integer.parseInt(request.getParameter(ORDER_ID_ATTRIBUTE)),
                Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE)));

        String message = Localization.getInstance().getLocalizedMessage(request, DELETE_PRODUCT_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        List<User> userList = userService.getAllUsersWithOrders();
        Map<User, Map<Order, Map<OrderProduct, Product>>> userMap = userService.getUserMap(userList);
        request.setAttribute(USER_MAP_ATTRIBUTE, userMap);

        return RespondFactory.builder()
                .request(request)
                .page("order")
                .build()
                .createPageFactory();
    }
}
