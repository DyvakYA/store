package controller.commands.order;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.services.UserService;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static model.constants.AttributesHolder.USER_MAP_ATTRIBUTE;

/**
 * This class represents admin's get all orders from base.
 *
 * @author dyvakyurii@gmail.com
 */
public class AdminGetAllOrdersCommand implements Command {

    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

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
