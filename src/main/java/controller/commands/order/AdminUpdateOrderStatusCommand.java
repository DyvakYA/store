package controller.commands.order;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.extras.Localization;
import model.services.OrderService;
import model.services.UserService;
import model.services.service.OrderServiceImpl;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.UPDATE_ORDER_SUCCESSFUL_MSG;

/**
 * This class represents admin update status of order.
 *
 * @author dyvakyurii@gmail.com
 */
public class AdminUpdateOrderStatusCommand implements Command {

    private OrderService orderService = OrderServiceImpl.getInstance();

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Order order = Order.builder()
                .setId(Integer.valueOf(request.getParameter(ORDER_ID_ATTRIBUTE)))
                .setOrderStatus(request.getParameter(ORDER_STATUS_ATTRIBUTE))
                .build();
        orderService.updateOrderStatus(order);

        request.setAttribute(RESULT_ATTRIBUTE, Localization.getInstance()
                .getLocalizedMessage(request, UPDATE_ORDER_SUCCESSFUL_MSG));

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
