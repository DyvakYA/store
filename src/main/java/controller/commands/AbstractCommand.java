package controller.commands;

import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.services.ProductService;
import model.services.UserService;
import model.services.service.OrderProductServiceImpl;
import model.services.service.ProductServiceImpl;
import model.services.service.UserOrderServiceImpl;
import model.services.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import static model.constants.AttributesHolder.*;
import static model.constants.UrlHolder.*;

/**
 * @author Dyvak Yurii dyvakyurii@gmail.com
 */
public abstract class AbstractCommand {

    private OrderProductServiceImpl orderProductService=OrderProductServiceImpl.getInstance();
    private UserOrderServiceImpl userOrderService=UserOrderServiceImpl.getInstance();
    private ProductService productService=ProductServiceImpl.getInstance();
    private UserServiceImpl userServiceImpl=UserServiceImpl.getInstance();
    private UserService userService=UserServiceImpl.getInstance();

     /**
     *  This role checker check the role of authorized user
     *  and depending on it return destination page
     */
    public String roleCheckerSetAttributes(String page, HttpServletRequest request) {
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(USER_SESSION_ATTRIBUTE);

        if (user == null) {
            if(page.equals(PRODUCT_JSP)) {
                request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());
            }
            return GUEST + page;
        }
        if (user.isAdmin()) {
            if(page.equals(PRODUCT_JSP)) {
                request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());
            }else if(page.equals(ORDER_JSP)){
                List<User> userList = userService.getAllUsersWithOrders();
                Map<User ,Map<Order, Map<OrderProduct, Product>>> userMap = userService.getUserMap(userList);
                request.setAttribute(USER_MAP_ATTRIBUTE, userMap);
            }else if(page.equals(USER_JSP)){
                request.setAttribute(USERS_LIST_ATTRIBUTE, userServiceImpl.getAll());
            }
            return WEB_INF_ADMIN + page;
        }
        if (!user.isAdmin()) {
            if(page.equals(PRODUCT_JSP)) {
                request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());
            }else if(page.equals(ORDER_JSP)){
                List<Order> orderList=userOrderService.getOrdersForUser(user.getId());
                Map<Order, Map<OrderProduct, Product>> orderMap=orderProductService.getOrdersMap(orderList);
                request.setAttribute(ORDER_MAP_ATTRIBUTE, orderMap);
            }
            return WEB_INF_USER + page;
        }
        return null;
    }

    public String roleCheckerDestinationPageReturner(String page, HttpServletRequest request) {
        HttpSession session=request.getSession();
        User user=(User) session.getAttribute(USER_SESSION_ATTRIBUTE);
        if (user == null) {
            return GUEST + page;
        }else if (user.isAdmin()) {
            return WEB_INF_ADMIN + page;
        }else if (!user.isAdmin()) {
            return WEB_INF_USER + page;
        }
        return null;
    }
}
