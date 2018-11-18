package controller.commands.user;

import controller.commands.Command;
import controller.commands.pageconstructor.RespondFactory;
import model.entities.Order;
import model.entities.OrderProduct;
import model.entities.Product;
import model.entities.User;
import model.extras.Localization;
import model.services.OrderProductService;
import model.services.OrderService;
import model.services.ProductService;
import model.services.UserOrderService;
import model.services.service.OrderProductServiceImpl;
import model.services.service.OrderServiceImpl;
import model.services.service.ProductServiceImpl;
import model.services.service.UserOrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static model.constants.AttributesHolder.*;
import static model.constants.MsgHolder.*;
import static model.constants.UrlHolder.INDEX;

/**
 * This class represents user add product to order command.
 *
 * @author dyvakyurii@gmail.com
 */
public class UserAddProductToOrderCommand implements Command {

    private OrderService orderService = OrderServiceImpl.getInstance();
    private OrderProductService orderProductService = OrderProductServiceImpl.getInstance();
    private UserOrderService userOrderService = UserOrderServiceImpl.getInstance();
    private ProductService productService = ProductServiceImpl.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);

        int productId = Integer.parseInt(request.getParameter(PRODUCT_ID_ATTRIBUTE));
        int orderId;
        int userId;
        int quantity = Integer.parseInt(request.getParameter(QUANTITY));

        if (user == null) {
            //set error message when user = null
            request.setAttribute(RESULT_ATTRIBUTE,
                    Localization.getLocalizedMessage
                            (request, USER_NOT_AUTHORIZED));

            request.setAttribute(PRODUCTS_LIST_ATTRIBUTE, productService.getAll());

            return RespondFactory.builder()
                    .request(request)
                    .page("product")
                    .build()
                    .createPageFactory();

        } else if (user.isBlocked()) {
            //set error message when user = null
            request.setAttribute(RESULT_ATTRIBUTE,
                    Localization.getLocalizedMessage
                            (request, ACCESS_DENIED));
            return INDEX;
        } else {
            userId = user.getId();
            //action when user != null
            if (session.getAttribute(ORDER_ID_ATTRIBUTE) == null) {
                //create Order
                Order order = orderService.createDefaultOrder();
                //set attribute order_id
                orderId = order.getId();
                session.setAttribute(ORDER_ID_ATTRIBUTE, orderId);
                //create UserOrder + OrderProduct
                orderProductService.createUserOrderAndOrderProduct(userId, orderId, productId, quantity);
                request.setAttribute(RESULT_ATTRIBUTE, Localization
                        .getLocalizedMessage(request, CREATE_USER_ORDER_SUCCESSFUL_MSG));
            } else if (session.getAttribute(ORDER_ID_ATTRIBUTE) != null) {
                orderId = Integer.parseInt(String.valueOf(session.getAttribute(ORDER_ID_ATTRIBUTE)));
                orderIdAttributeNotNull(request, productId, orderId, quantity);
            }
        }

        List<Order> orderList = userOrderService.getOrdersForUser(user.getId());
        Map<Order, Map<OrderProduct, Product>> orderMap = orderProductService.getOrdersMap(orderList);
        request.setAttribute(ORDER_MAP_ATTRIBUTE, orderMap);

        return RespondFactory.builder()
                .request(request)
                .page("user")
                .build()
                .createPageFactory();

    }

    private void orderIdAttributeNotNull(HttpServletRequest request, int productId, int orderId, int quantity) {
        //get orderProduct from data base
        Optional<OrderProduct> orderProductFromBase = orderProductService
                .getOrderProductByOrderIdAndProductId(orderId, productId);
        //action when orderProduct exist in base
        if (orderProductFromBase.isPresent()) {
            OrderProduct orderProduct = orderProductFromBase.get();
            orderProductService.increaseQuantityWhenAddProduct(orderProduct, quantity);
            request.setAttribute(RESULT_ATTRIBUTE, Localization
                    .getLocalizedMessage(request, AMOUNT_INCREASED));
            //action when orderProduct not exist in base
        }
        if (!orderProductFromBase.isPresent()) {
            //create orderProduct
            OrderProduct orderProduct = OrderProduct.builder()
                    .setOrderId(orderId)
                    .setProductId(productId)
                    .setQuantity(quantity)
                    .build();
            orderProductService.create(orderProduct);

            //set message about successful creation userOrder
            String message = Localization.getLocalizedMessage(request, CREATE_USER_ORDER_SUCCESSFUL_MSG);
            request.setAttribute(RESULT_ATTRIBUTE, message);
        }
    }
}
