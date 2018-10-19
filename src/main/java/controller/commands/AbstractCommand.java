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
