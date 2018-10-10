package controller.servlet;

import controller.commands.Command;
import controller.commands.order.AdminGetAllOrdersCommand;
import controller.commands.order.AdminUpdateOrderCommand;
import controller.commands.order.AdminUpdateOrderStatusCommand;
import controller.commands.order.DeleteOrderCommand;
import controller.commands.orderProduct.DeleteProductFromOrderCommand;
import controller.commands.product.*;
import controller.commands.user.*;
import controller.commands.userOrder.UserGetAllUserOrdersCommand;

import java.util.HashMap;
import java.util.Map;

import static model.constants.UrlHolder.*;

/**
 * This class is implementation of CommandHolder. It defines command for every supported request uri.
 *
 * @author dyvakyurii@gmail.com
 */
public class CommandHolder {

    protected static final String DELIMITER = ":";
    protected static final String GET = "GET";
    protected static final String POST = "POST";

    /**
     * Holder for GET commands
     */
    private Map<String, Command> commands = new HashMap<>();

    CommandHolder() {
        init();
    }

    private void init() {

        commands.put(GET + DELIMITER + LOCALE, new ChangeLocaleCommand());
        commands.put(GET + DELIMITER + LOGIN, new LoginCommand());
        commands.put(GET + DELIMITER + LOGOUT, new LogoutCommand());
        commands.put(GET + DELIMITER + PRODUCTS, new GetAllProductsCommand());
        commands.put(GET + DELIMITER + USER_PRODUCTS, new GetAllProductsCommand());
        commands.put(GET + DELIMITER + ADMIN_PRODUCTS, new GetAllProductsCommand());
        commands.put(GET + DELIMITER + ADMIN_DELETE_ORDER, new DeleteOrderCommand());
        commands.put(GET + DELIMITER + USER_DELETE_ORDER, new DeleteOrderCommand());
        commands.put(GET + DELIMITER + ADMIN_DELETE_PRODUCT_GET, new DeleteProductFromOrderCommand());
        commands.put(GET + DELIMITER + USER_DELETE_PRODUCT_GET, new DeleteProductFromOrderCommand());
        commands.put(GET + DELIMITER + FIND_PRODUCT_BY_PRICE, new FindProductsByPriceCommand());
        commands.put(GET + DELIMITER + FIND_PRODUCT_BY_NAME, new FindProductsByNameCommand());
        commands.put(GET + DELIMITER + USER_ORDERS, new UserGetAllUserOrdersCommand());
        commands.put(GET + DELIMITER + ADMIN_ORDERS, new AdminGetAllOrdersCommand());
        commands.put(GET + DELIMITER + ADMIN_GET_ALL_USERS, new AdminGetAllUsersCommand());
        commands.put(GET + DELIMITER + USER_ADD_TO_ORDER, new UserAddProductToOrderCommand());
        commands.put(GET + DELIMITER + ADMIN_UPDATE_ORDER, new AdminUpdateOrderCommand());
        commands.put(GET + DELIMITER + ADMIN_UPDATE_ORDER_STATUS, new AdminUpdateOrderStatusCommand());
        commands.put(GET + DELIMITER + REGISTRATION, new RegisterUserCommand());
        commands.put(POST + DELIMITER + ADMIN_GET_ALL_PRODUCTS_POST, new GetAllProductsCommand());
        commands.put(POST + DELIMITER + ADMIN_GET_ALL_USERS_POST, new AdminGetAllUsersCommand());
        commands.put(POST + DELIMITER + ADMIN_CREATE_USER, new CreateUserCommand());
        commands.put(POST + DELIMITER + ADMIN_UPDATE_USER, new UpdateUserCommand());
        commands.put(POST + DELIMITER + ADMIN_DELETE_USER, new DeleteUserCommand());
        commands.put(POST + DELIMITER + ADMIN_CREATE_PRODUCT, new CreateProductCommand());
        commands.put(POST + DELIMITER + ADMIN_UPDATE_PRODUCT, new UpdateProductCommand());
        commands.put(POST + DELIMITER + ADMIN_DELETE_PRODUCT_POST, new DeleteProductCommand());
    }

    /**
     * @param commandKey Key of the command, mapped to certain uri and request method
     * @return Command instance, mapped to certain uri and request method
     */
    public Command findCommand(String commandKey) {
        return commands.getOrDefault(commandKey, (req, resp) -> INDEX);
    }
}
