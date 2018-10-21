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
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.CREATE_ORDER_SUCCESSFUL_MSG;

/**
 * This class represents admin create order.
 *
 * @author dyvakyurii@gmail.com
 */
public class CreateOrderCommand implements Command {

    private OrderService orderService = OrderServiceImpl.getInstance();

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Order order = Order.builder()
                .setOrderStatus(request.getParameter(ORDER_STATUS_ATTRIBUTE))
                .setDate(new Timestamp(System.currentTimeMillis()))
                .build();
        orderService.create(order);

        List<User> userList = userService.getAllUsersWithOrders();
        Map<User, Map<Order, Map<OrderProduct, Product>>> userMap = userService.getUserMap(userList);
        request.setAttribute(USER_MAP_ATTRIBUTE, userMap);

        String message = Localization.getInstance().getLocalizedMessage(request, CREATE_ORDER_SUCCESSFUL_MSG);
        request.setAttribute(RESULT_ATTRIBUTE, message);

        return RespondFactory.builder()
                .request(request)
                .page("order")
                .build()
                .createPageFactory();
    }
}
